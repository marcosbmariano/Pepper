package com.example.marcos.test2.sentence_editor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.marcos.test2.PepperMVP;
import com.example.marcos.test2.R;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewPhraseFragListener} interface
 * to handle interaction events.
 */
public class NewPhraseFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "NPhraseFrag";
    private NewPhraseFragListener mListener;
    private Button mBTNAddPhrase;
    private Button mCancel;
    private EditText mEDTNewPhrase;
    @Inject PepperMVP.ModelRepository mPhrasesRepo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_phrase, container, false);
        setupViews(v);
        return v;
    }

    private void setupViews(View v){
        mEDTNewPhrase = (EditText)v.findViewById(R.id.edt_new_phrase);
        mEDTNewPhrase.requestFocus();
        mBTNAddPhrase = (Button)v.findViewById(R.id.btn_add_phrase);
        mBTNAddPhrase.setOnClickListener(this);
        mCancel = (Button)v.findViewById(R.id.btn_cancel);
        mCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_phrase:
                addNewPhrase();
                break;
            case R.id.btn_cancel:
                mListener.deleteFragment();
                break;
        }
    }

    private void addNewPhrase(){
        String sentence = mEDTNewPhrase.getText().toString().trim();
        ((PhrasesRepository) mPhrasesRepo).add(sentence);
        mListener.deleteFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewPhraseFragListener) {
            mListener = (NewPhraseFragListener) context;
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

    public interface NewPhraseFragListener {
        void deleteFragment();
    }
}
