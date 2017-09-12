package com.example.marcos.test2.sentence_editor;

import io.realm.RealmResults;

/**
 * TODO: Add class header comment.
 */

public class SentencesEditorMVP {

    public interface Model{

    }

    public interface View{
        void loadData(RealmResults<SentenceModel> models, int what);
    }

    public interface Presenter{
        void deleteView();
        void setView(View view);
        void loadFilesList();
        void receiveFilesFromRepo(RealmResults<SentenceModel> files);
        void closeRepositories();
    }

    public interface ModelRepository{
        void loadFiles(Presenter presenter);
        void closeRepository();


    }

}
