package com.example.marcos.test2.sentence_editor;

import android.util.Log;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoRule;

import java.util.List;

import javax.inject.Inject;

import static junit.framework.Assert.fail;


public class SentencesPresenterTest {
    SentencesPresenter mPresenter;
    SentencesRepository mRepository;
    @Mock
    SentencesEditorMVP.View mView;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new SentencesPresenter();
        //DaggerSentencesEditorComponent.builder().build().inject(mPresenter);
        mRepository = (SentencesRepository)mPresenter.mSentencesRepo;
    }


    @Test
    public void setView() throws Exception {
        mPresenter.setView(mView);
        mPresenter.loadFilesList();
        Mockito.verify(mView).loadData(mRepository.mSentences, 0);
    }

    @Test
    public void deleteView() throws Exception {

    }

    @Test
    public void loadFilesList() throws Exception {

    }

    @Test
    public void sendDataToView() throws Exception {

    }

}