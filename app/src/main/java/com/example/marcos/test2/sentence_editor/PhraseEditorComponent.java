package com.example.marcos.test2.sentence_editor;

import com.example.marcos.test2.application.PepperApplication;

import dagger.Component;

/**
 * TODO: Add class header comment.
 */
@PhrasesEditorScope
@Component(modules = PhrasesEditorModule.class)
public interface PhraseEditorComponent {
    void inject(PhrasesPresenterLoader loader);
    void inject(PhrasesPresenter presenter);
    void inject(NewPhraseFragment frag);
    void inject(PhrasesEditorFrag frag);
    void inject(PepperApplication application);
}
