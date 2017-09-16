package com.example.marcos.test2.sentence_editor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.marcos.test2.PepperMVP;
import com.example.marcos.test2.R;
import io.realm.RealmResults;

public class PhrasesEditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks, PepperMVP.View, NewPhraseFragment.NewPhraseFragListener {
    private static final String TAG = "SentencesActivity";
    private PepperMVP.Presenter mPhrasesPresenter;
    private FloatingActionButton mFab;
    private PhrasesPresenterLoader mPhPreLoader;
    private PhrasesEditorFrag mPhEdtFrag;
    private NewPhraseFragment mNewPhFrag;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases_editor);
        getSupportLoaderManager().initLoader(R.id.phrases_loader, null, this);
        setupViews();
    }

    private void setupViews(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mNewPhFrag = (NewPhraseFragment)getSupportFragmentManager().findFragmentById(R.id.frl_new_item);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        if( mNewPhFrag != null){
            mFab.setVisibility(View.INVISIBLE);
        }

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFab.setVisibility(View.INVISIBLE);
                mNewPhFrag = new NewPhraseFragment();
                mPhPreLoader.getComponent().inject(mNewPhFrag);
                FragmentTransaction transaction =
                        PhrasesEditorActivity.this.getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frl_new_item, mNewPhFrag);
                transaction.commit();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void deleteFragment() {
        if( mNewPhFrag != null){
            FragmentTransaction transaction =
                    PhrasesEditorActivity.this.getSupportFragmentManager().beginTransaction();
            transaction.remove(mNewPhFrag);
            transaction.commit();
            mFab.setVisibility(View.VISIBLE);
            mNewPhFrag = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //inject everything that shares lifecycle with the loader/activity
        if( mPhEdtFrag == null){
            mPhEdtFrag = (PhrasesEditorFrag)getSupportFragmentManager()
                    .findFragmentById(R.id.frag_sent_editor);
            mPhPreLoader.getComponent().inject(mPhEdtFrag);
        }
        if( mNewPhFrag != null){
            mPhPreLoader.getComponent().inject(mNewPhFrag);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //let presenter knows the Activity is ready to receive data
        mPhrasesPresenter.setView(this);
        mPhrasesPresenter.loadFilesList();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPhrasesPresenter.deleteView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( mPhrasesPresenter != null){
            mPhrasesPresenter.deleteView(); //TODO check this
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new PhrasesPresenterLoader(this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object presenter) {
        mPhPreLoader = (PhrasesPresenterLoader)loader;
        mPhrasesPresenter = (PepperMVP.Presenter) presenter;
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mPhrasesPresenter.deleteView();
        mPhrasesPresenter = null;
    }

    /**
     * This method is called by the Presenter to load data and the activity will manage the data
     */
    @Override
    public void loadData(RealmResults<PhraseModel> models, int what) {
        //PhraseEditorFrag cannot be null
        mPhEdtFrag.setData(models);
    }

}
