package com.example.marcos.test2.tasks;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * TODO: Add class header comment.
 */

public abstract class Task{
    private static final String TAG = "Task";
    private Handler mHandler;
    public static final int START_TASK = 0;
    public static final int SECONDARY_TASK = 1;
    public static final int THIRD_TASK = 2;
    public static final int RUN_ON_UI_THREAD = 3;
    private final String mTaskName;
    private Task [] mTaskList;


    public Task(String taskName){
        mTaskName = taskName;
        mTaskList = new Task [3];
    }

    public Task(Looper looper, String taskName){
        this(taskName);
        mHandler = generateHandler(looper);
    }

    public void initTask(Looper looper){
        mHandler = generateHandler(looper);
    }

    private Handler generateHandler(Looper looper){
        return new Handler(looper){
            @Override
            public void handleMessage(Message message) {
                switch (message.what){
                    case START_TASK:
                        sendEmptyMessage(mainTask(message));
                        break;
                    case SECONDARY_TASK:
                        startTask(SECONDARY_TASK, message);
                        break;
                    case THIRD_TASK:
                        startTask(THIRD_TASK, message);
                        break;
                    case RUN_ON_UI_THREAD:
                        sendToUIThread(message);
                        break;
                    default:
                        throw new IndexOutOfBoundsException( mTaskName + ", message.what " + message.what + " out of bounds!");
                }
            }

        };
    }

    private void startTask(int taskNumber, Message message){
        if( taskNumber < SECONDARY_TASK || taskNumber > THIRD_TASK ){
            throw new IndexOutOfBoundsException("Task number out of range");
        }else{
            Task task = mTaskList[taskNumber];

            if( task == null){
                mHandler.sendEmptyMessage(RUN_ON_UI_THREAD);
            }else{
                task.initTask(mHandler.getLooper());
                task.startTask(message);
            }
        }
    }

    public void startTask(){
        if( mHandler == null){
            throw new IllegalStateException("A Task cannot run without a Looper");
        }
        mHandler.sendEmptyMessage(START_TASK);
    }

    public void startTask(Message message){
        if( mHandler == null){
            throw new IllegalStateException("A Task cannot run without a Looper");
        }
        Message newMessage = mHandler.obtainMessage();
        newMessage.copyFrom(message);
        newMessage.what = START_TASK;
        newMessage.sendToTarget();
    }


    private void sendToUIThread(Message message){
        Handler UIhandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.e("Running on UI THREAD " , Thread.currentThread().getName());
            }
        };
        Message UImessage = UIhandler.obtainMessage();
        UImessage.copyFrom(message);
        UImessage.sendToTarget();

    }

    public String getTaskName(){
        return mTaskName;
    }

    public void setSecondaryTask(Task task){mTaskList[SECONDARY_TASK] = task;}

    public void setThirdTask(Task task){mTaskList[THIRD_TASK] = task;}


    protected abstract int mainTask(Message message);
}

