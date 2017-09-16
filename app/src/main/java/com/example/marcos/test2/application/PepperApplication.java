package com.example.marcos.test2.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.marcos.test2.PepperMVP;
import com.example.marcos.test2.sentence_editor.PhraseModel;
import com.example.marcos.test2.sentence_editor.PhrasesRepository;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.content.ContentValues.TAG;

/**
 * TODO: Add class header comment.
 */

public class PepperApplication extends Application {
    private static Context sContext;
    @Inject PepperMVP.ModelRepository mModelRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        Realm.init(PepperApplication.getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(PhrasesRepository.REALM_NAME)
                .build();
        Realm.setDefaultConfiguration(config);


    }

    public static Context getContext(){
        return sContext;
    }

}
