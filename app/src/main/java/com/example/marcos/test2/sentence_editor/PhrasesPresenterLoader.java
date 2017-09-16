package com.example.marcos.test2.sentence_editor;

import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.marcos.test2.PepperMVP;
import com.example.marcos.test2.application.PepperApplication;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Presenter l
 */

public class PhrasesPresenterLoader extends Loader<PepperMVP.Presenter>{
    private static String TAG = "Loader";
    PhraseEditorComponent mComponent;
    @Inject PepperMVP.Presenter mPhrasesPresenter;
    /**
     * @param context used to retrieve the application context.
     */
    public PhrasesPresenterLoader(Context context) {
        super(context);
        initializeRealm();
        mComponent = DaggerPhraseEditorComponent.builder().build();
        mComponent.inject(this);

    }

    private void initializeRealm(){
        Realm.init(PepperApplication.getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(PhrasesRepository.REALM_NAME)
                .build();
        Realm.setDefaultConfiguration(config);
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        mComponent.inject((PhrasesPresenter) mPhrasesPresenter);

        if(((PhrasesPresenter) mPhrasesPresenter).mPhrasesRepo == null ){ //TODO fix this
            Log.e(TAG, "FAIL");
        }
        deliverResult(mPhrasesPresenter);
    }

    @Override
    protected boolean onCancelLoad() {
        mPhrasesPresenter.closeRepositories();//TODO here or reset
        Log.e(TAG, "OnCancelLoad");
        return super.onCancelLoad();
    }

    @Override
    protected void onReset() {
        Log.e(TAG, "OnCancelLoad");
        //TODO should I delete Realm config
        super.onReset();
    }

    public PhraseEditorComponent getComponent(){
        return mComponent;
    }


}
