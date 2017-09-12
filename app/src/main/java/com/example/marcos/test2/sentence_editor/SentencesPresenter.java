package com.example.marcos.test2.sentence_editor;

import javax.inject.Inject;

import io.realm.RealmResults;


/**
 * TODO: Add class header comment.
 */

public class SentencesPresenter implements SentencesEditorMVP.Presenter {
    private static String TAG = "SentencesPresenter";
    @Inject
    SentencesEditorMVP.ModelRepository mSentencesRepo;
    SentencesEditorMVP.View mView;


    @Override
    public void deleteView() {
        mView = null;
    }

    @Override
    public void setView(SentencesEditorMVP.View view) {
        mView = view;
    }

    @Override
    public void loadFilesList() {
        mSentencesRepo.loadFiles(this);
    }

    @Override
    public void receiveFilesFromRepo(RealmResults<SentenceModel> files) {
        mView.loadData(files, 0);
    }

    @Override
    public void closeRepositories() {
        mSentencesRepo.closeRepository();
    }


}
