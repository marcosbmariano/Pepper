package com.example.marcos.test2.sentence_editor;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.marcos.test2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Add class header comment.
 */

public class SentencesEditorAdapter extends RecyclerView.Adapter<SentencesEditorAdapter.VH> {
    private static final String TAG ="SentEditAdapter";
    List<SentencesEditorMVP.Model> mData = new ArrayList<>();

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sentences_item, parent,false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.setText(((SentencesModel) mData.get(position)).getSentence());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setmData(List<SentencesEditorMVP.Model> models){
        mData.clear();
        mData.addAll(models);
        notifyDataSetChanged();
    }


    class VH extends RecyclerView.ViewHolder{
        private EditText mEDTSentence;
        public VH(View itemView) {
            super(itemView);

            mEDTSentence = (EditText) itemView.findViewById(R.id.edt_sentence);
            mEDTSentence.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {/* empty */}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.e(TAG, "OnTextChanged");
                    ((SentencesModel)mData.get(getAdapterPosition())).setSentence(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {/* empty */}
            });

            mEDTSentence.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if( !mEDTSentence.hasFocus()){
                        Log.e(TAG, "Lost Focus " + getAdapterPosition());
                        if( ((SentencesModel) mData.get(getAdapterPosition())).hasDataChanged() ){
                            ((SentencesModel) mData.get(getAdapterPosition())).save();
                        }
                    }
                }
            });

        }

        public void setText(String str){
            mEDTSentence.setText(str);
        }

    }

}
