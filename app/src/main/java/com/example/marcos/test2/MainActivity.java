package com.example.marcos.test2;

import android.content.Intent;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.aldebaran.qi.QiCallback;
import com.aldebaran.qi.sdk.Qi;
import com.aldebaran.qi.sdk.object.interaction.Say;
import com.example.marcos.test2.application.PepperApplication;
import com.example.marcos.test2.sentence_editor.PhraseModel;
import com.example.marcos.test2.sentence_editor.PhrasesEditorActivity;
import com.example.marcos.test2.tasks.Task;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A client needs Pepper to greet customers as they come to their store.
 * Ideally, Pepper can say several different sentences to greet people so as not to repeat
 * the same thing every time. It would be even better if the sentences are easily customizable
 */

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "SayActivity";
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }


    @Override
    protected void onResume() {
        super.onResume();
        startSampleTask();

    }
    /**
        This is what I have in mind that maybe could be used to chain actions from Pepper.
     Everything but the RUN_ON_UI_THREAD runs in a background thread (HandlerThread). The main idea
     is to separate each Task in an object that could trigger another tasks or report back to the UI
     thread, and based on the answer from the callbacks (or another means) a Task can be selected to be
     started or the background thread can be finished by issuing a command to the UI thread.
     Each task can send data to other Task or to the UI thread through a Message,
     This is very crude and it would need more time to test and make work properly
     */

    private void startSampleTask(){
        HandlerThread thread = new HandlerThread("Other THread", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Task firstTask = new Task(thread.getLooper(), "Greeting User Task"){

            @Override
            protected int mainTask(Message message) {
                Log.e(getTaskName(), "Hello human, how are you?");

                /**
                 * Some command can be send to Pepper here and based on the response
                 * another task can be selected to run next, and so on
                 */

                Random random = new Random();
                return random.nextBoolean() ? Task.SECONDARY_TASK : Task.THIRD_TASK;
            }
        };

        Task secondTask = new Task("Greeting Second task") {
            @Override
            protected int mainTask(Message message) {
                Log.e(getTaskName(), "Human answers ok, Further human interaction");
                return Task.SECONDARY_TASK;
            }
        };

        Task thirdTask = new Task("Gretting Third task") {
            @Override
            protected int mainTask(Message message) {
                Log.e(getTaskName(), "Human is moody, Don't want interaction, Not further human interaction");
                return Task.RUN_ON_UI_THREAD;
            }
        };
        firstTask.setThirdTask(thirdTask);
        firstTask.setSecondaryTask(secondTask);


        Task secondTaskStep2 = new Task("Second task continuation") {
            @Override
            protected int mainTask(Message message) {
                Log.e(getTaskName(), "Human say good by. No Further human interaction");
                return Task.RUN_ON_UI_THREAD;
            }
        };
        secondTask.setSecondaryTask(secondTaskStep2);

        firstTask.startTask();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sentences_editor:
                startActivity(new Intent(MainActivity.this, PhrasesEditorActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }




}
