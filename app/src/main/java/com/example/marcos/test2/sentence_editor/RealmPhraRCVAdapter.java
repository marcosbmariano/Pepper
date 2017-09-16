package com.example.marcos.test2.sentence_editor;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.marcos.test2.PepperMVP;
import com.example.marcos.test2.R;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * TODO: Add class header comment.
 */

public class RealmPhraRCVAdapter extends
        RealmRecyclerViewAdapter<PhraseModel,RealmPhraRCVAdapter.VH> {
    PhrasesRepository mPhrasesRepo;

    public RealmPhraRCVAdapter(@Nullable OrderedRealmCollection<PhraseModel> data,
                               boolean autoUpdate, PepperMVP.ModelRepository repo ) {
        super(data, autoUpdate);
        mPhrasesRepo = (PhrasesRepository)repo;
        setHasStableIds(true); //TODO check this
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phrase_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.setText(getData().get(position).getPhrase());
    }


    class VH extends RecyclerView.ViewHolder{
        private static final String TAG = "ViewHolder";
        private EditText mEDTPhrase;
        public VH(View itemView) {
            super(itemView);

            mEDTPhrase = (EditText) itemView.findViewById(R.id.edt_phrase);
            mEDTPhrase.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if( !mEDTPhrase.hasFocus()){
                        final String oldValue = getData().get(getAdapterPosition()).getPhrase();
                        final String phrase = mEDTPhrase.getText().toString().trim();

                        if( oldValue != phrase){
                            mPhrasesRepo.update(phrase, oldValue);
                        }
                    }
                }
            });

        }

        public void setText(String str){
            mEDTPhrase.setText(str);
        }


    }
}




























//