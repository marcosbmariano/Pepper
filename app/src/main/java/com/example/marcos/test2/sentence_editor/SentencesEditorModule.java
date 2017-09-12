package com.example.marcos.test2.sentence_editor;


import android.util.Log;

import com.example.marcos.test2.application.PepperApplication;

import java.util.Collections;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * TODO: Add class header comment.
 */

@Module
public class SentencesEditorModule {
    public static final String TAG ="SentenceSModule"; //TODO remove this

    @SentencesEditorScope
    @Provides
    public SentencesEditorMVP.ModelRepository provideRepository(){
        return new SentencesRepository();
    }

    @SentencesEditorScope
    @Provides
    public SentencesEditorMVP.Presenter providePresenter(){
        return new SentencesPresenter();
    }


    @SentencesEditorScope
    @Provides
    public SentencesRealmRCVAdapter provideRealmAdapter(){
        //Realm realm = Realm.getDefaultInstance();
       // SentencesRealmRCVAdapter result = new SentencesRealmRCVAdapter( realm.where(SentenceModel.class).findAll(), true);
        //realm.close();
        //return result;
        return new SentencesRealmRCVAdapter(null, true);
    }


}
