package com.example.marcos.test2;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aldebaran.qi.QiCallback;
import com.aldebaran.qi.sdk.Qi;
import com.aldebaran.qi.sdk.object.interaction.Say;
import com.example.marcos.test2.sentence_editor.SentencesEditorAdapter;
import com.example.marcos.test2.sentence_editor.SentencesEditorFrag;
import com.example.marcos.test2.sentence_editor.SentencesEditorMVP;
import com.example.marcos.test2.sentence_editor.SentencesPresenterLoader;

import java.util.List;

import javax.inject.Inject;

/**
 * A client needs Pepper to greet customers as they come to their store.
 * Ideally, Pepper can say several different sentences to greet people so as not to repeat
 * the same thing every time. It would be even better if the sentences are easily customizable
 */


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks,
        SentencesEditorMVP.View{
    private static final String TAG = "SayActivity";
    private SentencesEditorMVP.Presenter mSentencesPresenter;
    private SentencesPresenterLoader mSPLoader;
    @Inject SentencesEditorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "OnCreate");
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(R.id.sentencesLoaderPresenter, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //inject everything that shares lifecycle with the loader/activity
        mSPLoader.getComponent().inject(this);
        mSPLoader.getComponent().inject( //inject fragment
                (SentencesEditorFrag)getSupportFragmentManager()
                        .findFragmentById(R.id.frag_sent_editor));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSentencesPresenter.setView(this);
        mSentencesPresenter.loadFilesList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSentencesPresenter.deleteView();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new SentencesPresenterLoader(this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object presenter) {
        mSPLoader = (SentencesPresenterLoader)loader;
        mSentencesPresenter = (SentencesEditorMVP.Presenter) presenter;
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mSentencesPresenter.deleteView();
        mSentencesPresenter = null;
    }

    /**
     * This method is called by the Presenter to the activity that will manage and distribute all data
     * to any fragment
     */

    @Override
    public void loadData(List<SentencesEditorMVP.Model> models, int what) {
        mAdapter.setmData(models);
    }

    private void sayHello(){
        Say say = new Say(this);
        say.run("Hello, world!").then(Qi.onUiThread(new QiCallback<Void>() {
            @Override
            public void onResult(Void ignore) {
                Log.d(TAG, "result on thread " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "error", error);
            }

            @Override
            public void onCancel() {
                Log.w(TAG, "cancel");
            }
        }));

    }
}
