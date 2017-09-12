package com.example.marcos.test2.sentence_editor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.marcos.test2.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewSentenceFragment.NewSentenceFragListener} interface
 * to handle interaction events.
 */
public class NewSentenceFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "NSentenceFrag";
    private NewSentenceFragListener mListener;
    private Button mBTNAddSentence;
    private Button mCancel;
    private EditText mEDTNewSentence;


    public NewSentenceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_sentence, container, false);
        setupViews(v);
        return v;
    }

    private void setupViews(View v){
        mEDTNewSentence = (EditText)v.findViewById(R.id.edt_new_sentence);
        mEDTNewSentence.requestFocus();
        mBTNAddSentence = (Button)v.findViewById(R.id.btn_add_sentence);
        mBTNAddSentence.setOnClickListener(this);
        mCancel = (Button)v.findViewById(R.id.btn_cancel);
        mCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_sentence:
                addNewSentence();
                break;
            case R.id.btn_cancel:
                mListener.deleteFragment();
                break;
        }
    }

    private void addNewSentence(){
        Log.e(TAG, "Add new Sentence");

        mListener.deleteFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewSentenceFragListener) {
            mListener = (NewSentenceFragListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface NewSentenceFragListener {
        // TODO: Update argument type and name
        void deleteFragment();
    }
}
