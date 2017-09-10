package com.example.marcos.test2.sentence_editor;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * TODO: Add class header comment.
 */

@Module
public class SentencesEditorModule {

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
    public SentencesEditorAdapter provideAdapter(){
        return new SentencesEditorAdapter();
    }

}
