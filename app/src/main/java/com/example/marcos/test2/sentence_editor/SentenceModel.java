package com.example.marcos.test2.sentence_editor;





import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * TODO: Add class header comment.
 */

public class SentenceModel extends RealmObject implements SentencesEditorMVP.Model {
    private static final String TAG = "SentenceModel";

    @Required private String mSentence;

    public String getSentence(){
        return mSentence;
    }

    public void setSentence(final String newSentence){
            mSentence = newSentence;
    }


}
