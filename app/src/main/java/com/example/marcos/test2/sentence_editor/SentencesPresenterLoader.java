package com.example.marcos.test2.sentence_editor;

import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.marcos.test2.application.PepperApplication;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Presenter l
 */

public class SentencesPresenterLoader extends Loader<SentencesEditorMVP.Presenter>{
    private static String TAG = "Loader";
    SentencesEditorComponent mComponent;
    @Inject SentencesEditorMVP.Presenter mSentencesPresenter;
    /**
     * @param context used to retrieve the application context.
     */
    public SentencesPresenterLoader(Context context) {
        super(context);
        initializeRealm();
        mComponent = DaggerSentencesEditorComponent.builder().build();
        mComponent.inject(this);

    }

    private void initializeRealm(){
        Realm.init(PepperApplication.getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(SentencesRepository.REALM_NAME)
                .build();
        Realm.setDefaultConfiguration(config);
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        mComponent.inject((SentencesPresenter)mSentencesPresenter);

        if(((SentencesPresenter) mSentencesPresenter).mSentencesRepo == null ){ //TODO fix this
            Log.e(TAG, "FAIL");
        }
        deliverResult(mSentencesPresenter);
    }

    @Override
    protected boolean onCancelLoad() {
        mSentencesPresenter.closeRepositories();//TODO here or reset
        Log.e(TAG, "OnCancelLoad");
        return super.onCancelLoad();
    }

    @Override
    protected void onReset() {
        Log.e(TAG, "OnCancelLoad");
        //TODO should I delete Realm config
        super.onReset();
    }

    public SentencesEditorComponent getComponent(){
        return mComponent;
    }


}
