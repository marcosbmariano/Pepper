package com.example.marcos.test2.sentence_editor;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Add class header comment.
 */

public class SentencesRepository implements SentencesEditorMVP.ModelRepository {
    List<SentencesEditorMVP.Model> mSentences = new ArrayList<>();
    {
        mSentences.add(new SentencesModel("Hello"));
        mSentences.add(new SentencesModel("How are you"));
        mSentences.add(new SentencesModel("Good morning"));

    }

    /**
     * this should make an asynchronous call to load files
     */
    @Override
    public void loadFiles(SentencesEditorMVP.Presenter presenter) {
        presenter.receiveFilesFromRepo(mSentences);
    }



}
