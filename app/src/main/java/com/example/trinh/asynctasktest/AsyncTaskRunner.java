package com.example.trinh.asynctasktest;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class AsyncTaskRunner extends AsyncTask<String, Void, Bitmap> {
    private static final int LAYOUT_PARAM = 100;
    private static final String ERROR_MESSAGE = "Some error occurred!";

    private final WeakReference<MainActivity> activityReference;

    AsyncTaskRunner(final MainActivity context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        final String url = urls[0];
        return getBitmap(url);
    }

    @Override
    protected void onPreExecute() {
        startProgressBar();
        freezeWindow();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        final MainActivity mainActivity = activityReference.get();
        final ImageView image = activityReference.get().getImage();

        stopProgressBar();
        UnfreezeWindow();

        if (bitmap != null) {
            image.setImageBitmap(bitmap);
        } else {
            Toast.makeText(mainActivity, ERROR_MESSAGE, Toast.LENGTH_LONG).show();
        }
    }

    @Nullable
    private Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        try (final InputStream in = (InputStream) new URL(url).getContent()) {
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private void startProgressBar() {
        activityReference.get().getProgressBar().setProgressTintList(ColorStateList.valueOf(Color.RED));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LAYOUT_PARAM, LAYOUT_PARAM);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        activityReference.get().getProgressBar().setVisibility(View.VISIBLE);
    }

    private void freezeWindow() {
        activityReference.get().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void stopProgressBar() {
        activityReference.get().getProgressBar().setVisibility(View.GONE);
    }

    private void UnfreezeWindow() {
        activityReference.get().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

}
