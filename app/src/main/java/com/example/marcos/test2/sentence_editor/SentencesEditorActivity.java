package com.example.marcos.test2.sentence_editor;

import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.marcos.test2.R;

import javax.inject.Inject;

import io.realm.RealmResults;

public class SentencesEditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks, SentencesEditorMVP.View{
    private static final String TAG = "SentencesActivity";
    private SentencesEditorMVP.Presenter mSentencesPresenter;
    private FloatingActionButton mFab;
    private SentencesPresenterLoader mSPLoader;
    @Inject
    SentencesRealmRCVAdapter mRCVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentences_editor);
        getSupportLoaderManager().initLoader(R.id.sentencesLoaderPresenter, null, this);
        setupViews();
    }

    private void setupViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();}
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //inject everything that shares lifecycle with the loader/activity
        mSPLoader.getComponent().inject(this);
        mSPLoader.getComponent().inject(
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
     * This method is called by the Presenter to the activity that will manage and
     * distribute all data.
     */
    @Override
    public void loadData(RealmResults<SentenceModel> models, int what) {
        mRCVAdapter.updateData(models);
    }
}
