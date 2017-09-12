package com.example.marcos.test2.sentence_editor;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.marcos.test2.R;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

/**
 * TODO: Add class header comment.
 */

public class SentencesRealmRCVAdapter extends
        RealmRecyclerViewAdapter<SentenceModel,SentencesRealmRCVAdapter.VH> {

    public SentencesRealmRCVAdapter(@Nullable OrderedRealmCollection<SentenceModel> data, boolean autoUpdate) {
        super(data, autoUpdate);
        setHasStableIds(true);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sentences_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.setText(getData().get(position).getSentence());
    }


    class VH extends RecyclerView.ViewHolder{
        private static final String TAG = "ViewHolder";
        private EditText mEDTSentence;
        public VH(View itemView) {
            super(itemView);

            mEDTSentence = (EditText) itemView.findViewById(R.id.edt_sentence);


            mEDTSentence.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if( !mEDTSentence.hasFocus()){
                        final String oldValue = getData().get(getAdapterPosition()).getSentence();
                        final String sentence = mEDTSentence.getText().toString().trim();

                        if( oldValue != sentence){
                            Realm realm = Realm.getDefaultInstance();

                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    SentenceModel model = realm.where(SentenceModel.class).equalTo("mSentence", oldValue).findFirst();
                                    model.setSentence(sentence);
                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {
                                    Log.e(TAG, error.getMessage());
                                }
                            });
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































//