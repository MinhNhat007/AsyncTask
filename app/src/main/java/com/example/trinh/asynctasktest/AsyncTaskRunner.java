package com.example.trinh.asynctasktest;

import android.graphics.Color;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AsyncTaskRunner extends AsyncTask<String, Integer, List<String>> {
    private final WeakReference<MainActivity> activityReference;

    AsyncTaskRunner(final MainActivity context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        startProgressBar();
        writeMessage(Color.BLUE, "Starting task....");
    }

    @Override
    protected List<String> doInBackground(String... tasks) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < tasks.length; i++) {
            result.add(tasks[i]);
            sleep(1);
            int percent = getPercentInCurrentTask(tasks.length, i);
            publishProgress(percent);
            if (isCancelled()) {
                break;
            }
        }

        return result;
    }

    @Override
    protected void onCancelled() {
        writeMessage(Color.RED, "Operation is cancel...");
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        writeMessage(Color.BLUE, String.format(Locale.US, "Completed....%d%%", progress[0]));
    }

    @Override
    protected void onPostExecute(List<String> results) {
        writeMessage(Color.BLUE, "Done....");
        for (String result : results) {
            writeMessage(Color.BLUE, result);
        }

        stopProgressBar();
    }

    private void writeMessage(int color, String message) {
        final TextView mTextView = activityReference.get().getMTextView();
        mTextView.setTextColor(color);
        mTextView.setText(TextUtils.concat(mTextView.getText(), "\n", message));
    }

    private void startProgressBar() {
        activityReference.get().getMProgressBar().setVisibility(View.VISIBLE);
    }

    private void stopProgressBar() {
        activityReference.get().getMProgressBar().setVisibility(View.GONE);
    }

    private int getPercentInCurrentTask(float sum, int current) {
        return (int) (((current + 1) / sum) * 100);
    }

    private void sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
