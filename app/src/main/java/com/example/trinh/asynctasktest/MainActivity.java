package com.example.trinh.asynctasktest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private AsyncTask<String, Integer, List<String>> mMyTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressbar);
        mTextView = findViewById(R.id.tv);
    }

    public void buttonStartAction(View view) {
        clearText();
        mMyTask = new AsyncTaskRunner(this);
        mMyTask.execute("Task 1", "Task 2", "Task 3", "Task 4", "Task 5");
    }

    public void buttonCancelAction(View view) {
        mMyTask.cancel(true);
    }

    private void clearText() {
        mTextView.setText("");
    }

    public TextView getMTextView() {
        return mTextView;
    }

    public ProgressBar getMProgressBar() {
        return mProgressBar;
    }
}