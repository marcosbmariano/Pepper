package com.example.marcos.test2.sentence_editor;

import android.util.Log;

/**
 * TODO: Add class header comment.
 */

public class SentencesModel implements SentencesEditorMVP.Model {
    private static final String TAG = "SentencesModel";
    private String mSentence;
    private boolean hasSentenceChanged;

    public SentencesModel(String sentence){
        mSentence = sentence;
    }

    public String getSentence(){
        return mSentence;
    }

    public void setSentence(String newSentence){
        mSentence = newSentence;
        hasSentenceChanged = true;
    }
    public boolean hasDataChanged(){
        return hasSentenceChanged;
    }

    public void save(){
        Log.e(TAG, "SAVE");
        hasSentenceChanged = false;
    }

}
