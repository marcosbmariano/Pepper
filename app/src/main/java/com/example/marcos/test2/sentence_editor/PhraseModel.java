package com.example.marcos.test2.sentence_editor;





import com.example.marcos.test2.PepperMVP;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * TODO: Add class header comment.
 */

public class PhraseModel extends RealmObject implements PepperMVP.Model {
    private static final String TAG = "PhraseModel";

    @Required private String mPhrase;
    private long mUses;

    public String getPhrase(){
        return mPhrase;
    }

    public void setPhrase(final String newPhrase){
            mPhrase = newPhrase;
    }

    public long getUses() {
        return mUses;
    }

    public void setUses(long uses) {
        mUses = uses;
    }


}
