package com.example.trinh.asynctasktest;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

import lombok.SneakyThrows;

public class AsyncTaskRunner extends AsyncTask<String, String, String> {
    private static final int LAYOUT_PARAM = 100;
    private final WeakReference<MainActivity> activityReference;

    AsyncTaskRunner(final MainActivity context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    @SneakyThrows
    protected String doInBackground(String... params) {
        publishProgress("Sleeping...Freezing button");

        final String numberAsString = params[0];
        int time = convertToSecond(numberAsString);

        Thread.sleep(time);

        return "Slept for " + numberAsString + " seconds";
    }

    @Override
    protected void onPostExecute(String result) {
        stopProgressBar();
        UnfreezeWindow();

        activityReference.get().getFinalResult().setText(result);
    }

    @Override
    protected void onPreExecute() {
        startProgressBar();
        freezeWindow();
    }

    @Override
    protected void onProgressUpdate(String... text) {
        final String message = text[0];
        activityReference.get().getFinalResult().setText(message);

    }

    private void freezeWindow() {
        activityReference.get().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void UnfreezeWindow() {
        activityReference.get().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void startProgressBar() {
        activityReference.get().getProgressBar().setProgressTintList(ColorStateList.valueOf(Color.RED));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LAYOUT_PARAM, LAYOUT_PARAM);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        activityReference.get().getProgressBar().setVisibility(View.VISIBLE);
    }

    private void stopProgressBar() {
        activityReference.get().getProgressBar().setVisibility(View.GONE);
    }

    private int convertToSecond(String numberAsString) {
        return Integer.parseInt(numberAsString) * 1000;
    }
}
