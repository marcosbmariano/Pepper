package com.example.marcos.test2.sentence_editor;

import com.example.marcos.test2.PepperMVP;

import javax.inject.Inject;

import io.realm.RealmResults;


/**
 * TODO: Add class header comment.
 */

public class PhrasesPresenter implements PepperMVP.Presenter {
    private static String TAG = "PhrasesPresenter";
    RealmResults<PhraseModel> mCache;
    @Inject
    PepperMVP.ModelRepository mPhrasesRepo;
    PepperMVP.View mView;


    @Override
    public void deleteView() {
        mView = null;
    }

    @Override
    public void setView(PepperMVP.View view) {  //TODO test this
        mView = view;
        if( mCache != null){
            mView.loadData(mCache, 0);
            mCache = null;
        }
    }

    @Override
    public void loadFilesList() {
        mPhrasesRepo.loadFiles(this);
    }

    @Override
    public void receiveFilesFromRepo(RealmResults<PhraseModel> files) {
        if( mView != null){
            mView.loadData(files, 0);
            mCache = null;
        }else{
            mCache = files;
        }

    }

    @Override
    public void closeRepositories() {
        mPhrasesRepo.closeRepository();
    }


}
