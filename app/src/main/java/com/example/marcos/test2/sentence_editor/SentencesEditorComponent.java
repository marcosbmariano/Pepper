package com.example.marcos.test2.sentence_editor;

import android.content.Loader;

import com.example.marcos.test2.MainActivity;

import dagger.Component;

/**
 * TODO: Add class header comment.
 */
@SentencesEditorScope
@Component(modules = SentencesEditorModule.class)
public interface SentencesEditorComponent {
    void inject(SentencesPresenterLoader loader);
    void inject(SentencesPresenter presenter);
    void inject(SentencesEditorActivity activity);
    void inject(SentencesEditorFrag frag);
}
