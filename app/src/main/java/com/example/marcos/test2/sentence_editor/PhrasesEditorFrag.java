package com.example.marcos.test2.sentence_editor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marcos.test2.R;

import javax.inject.Inject;

import io.realm.RealmResults;


public class PhrasesEditorFrag extends Fragment {
    private static final String TAG = "SentEdFrag";
    private RecyclerView mRCView;
    @Inject
    RealmPhraRCVAdapter mRCVAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phrases_editor, container, false);
        setupViews(v);
        return v;
    }

    private void setupViews(View v){
        mRCView = (RecyclerView)v.findViewById(R.id.rcv_phrase_editor);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRCView.setAdapter(mRCVAdapter);
    }

    public void setData(RealmResults<PhraseModel> models){
        mRCVAdapter.updateData(models);
    }



}
