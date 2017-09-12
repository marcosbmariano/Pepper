package com.example.marcos.test2;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aldebaran.qi.QiCallback;
import com.aldebaran.qi.sdk.Qi;
import com.aldebaran.qi.sdk.object.interaction.Say;
import com.example.marcos.test2.sentence_editor.SentenceModel;
import com.example.marcos.test2.sentence_editor.SentencesEditorActivity;
import com.example.marcos.test2.sentence_editor.SentencesEditorFrag;
import com.example.marcos.test2.sentence_editor.SentencesEditorMVP;
import com.example.marcos.test2.sentence_editor.SentencesPresenterLoader;
import com.example.marcos.test2.sentence_editor.SentencesRealmRCVAdapter;

import javax.inject.Inject;

import io.realm.RealmResults;

/**
 * A client needs Pepper to greet customers as they come to their store.
 * Ideally, Pepper can say several different sentences to greet people so as not to repeat
 * the same thing every time. It would be even better if the sentences are easily customizable
 */


public class MainActivity extends AppCompatActivity{
    private static final String TAG = "SayActivity";
    //private SentencesEditorMVP.Presenter mSentencesPresenter;
    //private SentencesPresenterLoader mSPLoader;
    //@Inject SentencesRealmRCVAdapter mRCVAdapter;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews(){
        mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SentencesEditorActivity.class));
            }
        });
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        //inject everything that shares lifecycle with the loader/activity
//        mSPLoader.getComponent().inject(this);
//        mSPLoader.getComponent().inject( //inject fragment
//                (SentencesEditorFrag)getSupportFragmentManager()
//                        .findFragmentById(R.id.frag_sent_editor));
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mSentencesPresenter.setView(this);
//        mSentencesPresenter.loadFilesList();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        //mSentencesPresenter.deleteView();
//    }

//    @Override
//    public Loader onCreateLoader(int id, Bundle args) {
//        return new SentencesPresenterLoader(this);
//    }

//    @Override
//    public void onLoadFinished(Loader loader, Object presenter) {
//        mSPLoader = (SentencesPresenterLoader)loader;
//        mSentencesPresenter = (SentencesEditorMVP.Presenter) presenter;
//    }

//    @Override
//    public void onLoaderReset(Loader loader) {
//        mSentencesPresenter.deleteView();
//        mSentencesPresenter = null;
//    }

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
