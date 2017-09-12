package com.example.marcos.test2.sentence_editor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marcos.test2.R;

import javax.inject.Inject;


public class SentencesEditorFrag extends Fragment {
    private static final String TAG = "SentEdFrag";
    private RecyclerView mRCView;
    @Inject SentencesRealmRCVAdapter mRCVAdapter;

    public SentencesEditorFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sentences_editor, container, false);
        setupViews(v);
        return v;
    }

    private void setupViews(View v){
        mRCView = (RecyclerView)v.findViewById(R.id.rcv_sent_editor);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRCView.setAdapter(mRCVAdapter);
    }



}
