package com.example.marcos.test2.sentence_editor;



import com.example.marcos.test2.PepperMVP;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;


/**
 * TODO: Add class header comment.
 */

@Module
public class PhrasesEditorModule {

    @PhrasesEditorScope
    @Provides
    public PepperMVP.ModelRepository provideRepository(Realm realm){
        return new PhrasesRepository(realm);
    }

    @PhrasesEditorScope
    @Provides
    public PepperMVP.Presenter providePresenter(){
        return new PhrasesPresenter();
    }


    @PhrasesEditorScope
    @Provides
    public RealmPhraRCVAdapter provideRealmAdapter(PepperMVP.ModelRepository repo){
        return new RealmPhraRCVAdapter(null, true, repo);
    }

    @PhrasesEditorScope
    @Provides
    public Realm provideRealmDefaultInstance(){
        return Realm.getDefaultInstance();
    }





}
