package com.example.marcos.test2.sentence_editor;

import android.util.Log;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * TODO: Add class header comment.
 */

public class SentencesRepository implements SentencesEditorMVP.ModelRepository {
    public static final String REALM_NAME = "myrealm.realm";
    private Realm mRealm;
    private RealmResults<SentenceModel> mCache = null;

    public SentencesRepository(){

    }

    /**
      this make an asynchronous call to the Realm database to load files
     */
    @Override
    public void loadFiles(final SentencesEditorMVP.Presenter presenter) {

        if( mRealm == null || mRealm.isClosed()){ mRealm = Realm.getDefaultInstance();}

        RealmResults<SentenceModel> result = mRealm.where(SentenceModel.class)
                .findAllAsync();
        result.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<SentenceModel>>() {
            @Override
            public void onChange(RealmResults<SentenceModel> sentencesModels,
                                 OrderedCollectionChangeSet changeSet) {

                if( sentencesModels.isEmpty()){
                    create("Hello");
                    create("How are you");
                    create("Good morning");
                }else{
                    mCache = sentencesModels;
                    presenter.receiveFilesFromRepo(sentencesModels);
                }

            }
        });
    }


    public void update(final String sentence, final SentenceModel model){

        mRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        model.setSentence(sentence);
                        Log.e(TAG, "Adding " + sentence);
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


    public void create(final String sentence){

        mRealm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        SentenceModel model = realm.createObject(SentenceModel.class);
                        model.setSentence(sentence);
                        Log.e(TAG, "Adding " + sentence);
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

    @Override
    public void closeRepository() {
        if( mRealm != null){
            mRealm.close();
        }
    }



}
