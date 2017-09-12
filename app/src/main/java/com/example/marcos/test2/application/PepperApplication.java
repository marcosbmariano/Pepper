package com.example.marcos.test2.application;

import android.app.Application;
import android.content.Context;

import com.example.marcos.test2.sentence_editor.SentencesRepository;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * TODO: Add class header comment.
 */

public class PepperApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        Realm.init(PepperApplication.getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(SentencesRepository.REALM_NAME)
                .build();
        Realm.setDefaultConfiguration(config);

    }

    public static Context getContext(){
        return sContext;
    }
}
