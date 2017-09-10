package com.example.marcos.test2.sentence_editor;

import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;

import javax.inject.Inject;

/**
 * TODO: Add class header comment.
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
        mComponent = DaggerSentencesEditorComponent.builder().build();
        mComponent.inject(this);
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        mComponent.inject((SentencesPresenter)mSentencesPresenter);

        if(((SentencesPresenter) mSentencesPresenter).mSentencesRepo == null ){
            Log.e(TAG, "FAIL");
        }
        deliverResult(mSentencesPresenter);
    }

    @Override
    protected boolean onCancelLoad() {

        return super.onCancelLoad();
    }

    public SentencesEditorComponent getComponent(){
        return mComponent;
    }


}
