package com.example.marcos.test2.sentence_editor;

import android.util.Log;

import com.example.marcos.test2.PepperMVP;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * This class is responsible to load data from Realm
 * After this class is no longer needed, call closeRepository
 */

public class PhrasesRepository implements PepperMVP.ModelRepository {
    public static final String REALM_NAME = "myrealm.realm";
    private static final String TAG = "PhraseRepo";
    private Realm mRealm;
    RealmResults<PhraseModel> mCache = null; //Package access for testing

    public PhrasesRepository(Realm realm){
        mRealm = realm;
    }

    /**
      this make an asynchronous call to the Realm database to load files
     */
    @Override
    public void loadFiles(final PepperMVP.Presenter presenter) {
        //TODO remove if( mRealm == null || mRealm.isClosed()){ mRealm = Realm.getDefaultInstance();}

        if( mCache == null){ //if mCache is null, load result from Realm, else, load cache
            RealmResults<PhraseModel> result = mRealm.where(PhraseModel.class)
                    .findAllAsync();
            result.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<PhraseModel>>() {
                @Override
                public void onChange(RealmResults<PhraseModel> phrasesModels,
                                     OrderedCollectionChangeSet changeSet) {

                    //this is an ugly solution to insert some data when the app is first installed
                    if( phrasesModels.isEmpty()){
                        add("Hello");
                        add("How are you");
                        add("Good morning");
                    }else{
                        // add a cache in case of Activity destruction/recreation
                        mCache = phrasesModels;
                        presenter.receiveFilesFromRepo(phrasesModels);
                    }
                }
            });
        }else{
            presenter.receiveFilesFromRepo(mCache);
        }
    }

    /**
     * When repository is closed, close Realm, and because Realm instance is a Dagger Singleton
     * All of its copies will also be closed
     */
    @Override //
    public void closeRepository() {
        if( mRealm != null){
            mRealm.close();
        }
    }

    void add(final String phrase){

        mRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        PhraseModel model = realm.createObject(PhraseModel.class);
                        model.setPhrase(phrase);
                        Log.e(TAG, "Adding " + phrase);
                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "Transaction was a success.");
                    }
                }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Log.e(TAG, "Transaction failed and was automatically canceled. " + error.getMessage());
                }
        });
    }

    void update(final String newValue, final String oldValue){
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                PhraseModel model = realm.where(PhraseModel.class).equalTo("mPhrase", oldValue).findFirst();
                model.setPhrase(newValue);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, error.getMessage());
            }
        });

    }





}
