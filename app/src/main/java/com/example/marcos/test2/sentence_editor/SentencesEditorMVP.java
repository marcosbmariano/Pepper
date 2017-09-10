package com.example.marcos.test2.sentence_editor;

import java.util.List;

/**
 * TODO: Add class header comment.
 */

public class SentencesEditorMVP {

    public interface Model{

    }

    public interface View{
        void loadData(List<Model> models, int what);
    }

    public interface Presenter{
        void deleteView();
        void setView(View view);
        void loadFilesList();
        void receiveFilesFromRepo(List<SentencesEditorMVP.Model> files);
    }

    public interface ModelRepository{
        void loadFiles(Presenter presenter);


    }

}
