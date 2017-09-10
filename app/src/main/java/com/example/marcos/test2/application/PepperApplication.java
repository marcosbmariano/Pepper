package com.example.marcos.test2.application;

import android.app.Application;

import io.realm.Realm;

/**
 * TODO: Add class header comment.
 */

public class PepperApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
