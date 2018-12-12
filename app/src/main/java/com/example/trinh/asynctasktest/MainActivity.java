package com.example.trinh.asynctasktest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private Button button;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.image);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressbar);
    }

    public void buttonAction(View view) {
        final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(MainActivity.this);
        final List<String> data = DataProvider.provideData();

        asyncTaskRunner.execute(data.get(0));
    }

    public ImageView getImage() {
        return this.image;
    }

    public Button getButton() {
        return this.button;
    }

    public ProgressBar getProgressBar() {
        return this.progressBar;
    }
}
