package com.example.marcos.test2.sentence_editor;

import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.marcos.test2.R;

import javax.inject.Inject;

import io.realm.RealmResults;

public class SentencesEditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks, SentencesEditorMVP.View, NewSentenceFragment.NewSentenceFragListener {
    private static final String TAG = "SentencesActivity";
    private SentencesEditorMVP.Presenter mSentencesPresenter;
    private FloatingActionButton mFab;
    private SentencesPresenterLoader mSPLoader;
    private NewSentenceFragment mNSFrag;
    private Toolbar mToolbar;
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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mNSFrag = (NewSentenceFragment)getSupportFragmentManager().findFragmentById(R.id.frl_new_item);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        if( mNSFrag != null){
            mFab.setVisibility(View.INVISIBLE);
        }

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFab.setVisibility(View.INVISIBLE);
                mNSFrag = new NewSentenceFragment();
                FragmentTransaction transaction =
                        SentencesEditorActivity.this.getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frl_new_item, mNSFrag);
                transaction.commit();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void deleteFragment() {

        if( mNSFrag != null){
            Log.e(TAG, "DeleteFrag");
            FragmentTransaction transaction =
                    SentencesEditorActivity.this.getSupportFragmentManager().beginTransaction();
            transaction.remove(mNSFrag);
            transaction.commit();
            mFab.setVisibility(View.VISIBLE);
            mNSFrag = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //inject everything that shares lifecycle with the loader/activity
        mSPLoader.getComponent().inject(this);
        mSPLoader.getComponent().inject(
                (SentencesEditorFrag)getSupportFragmentManager()
                        .findFragmentById(R.id.frag_sent_editor));
    }

    @Override
    public void onResume() {
        super.onResume();
        mSentencesPresenter.setView(this);
        mSentencesPresenter.loadFilesList();
    }

    @Override
    public void onPause() {
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
