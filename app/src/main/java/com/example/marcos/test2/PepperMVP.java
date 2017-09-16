package com.example.marcos.test2;

import com.example.marcos.test2.sentence_editor.PhraseModel;

import io.realm.RealmResults;

/**
 * TODO: Add class header comment.
 */

public class PepperMVP {

    public interface Model{

    }

    public interface View{
        void loadData(RealmResults<PhraseModel> models, int what);
    }

    public interface Presenter{
        void deleteView();
        void setView(View view);
        void loadFilesList();
        void receiveFilesFromRepo(RealmResults<PhraseModel> files);
        void closeRepositories();
    }

    public interface ModelRepository{
        void loadFiles(Presenter presenter);
        void closeRepository();


    }

}
