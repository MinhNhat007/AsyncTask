package com.example.trinh.asynctasktest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import lombok.Getter;

@Getter
public class MainActivity extends AppCompatActivity {

    private EditText time;
    private Button button;
    private TextView finalResult;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpComponents();
        doTaskButtonClicking();
    }

    private void setUpComponents() {
        time = findViewById(R.id.in_time);
        button = findViewById(R.id.btn_run);
        finalResult = findViewById(R.id.tv_result);
        progressBar = findViewById(R.id.progressbar);
    }

    private void doTaskButtonClicking() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskRunner runner = new AsyncTaskRunner(MainActivity.this);
                final String sleepTime = time.getText().toString();
                runner.execute(sleepTime);
            }
        });
        /*button.setOnClickListener((View.OnClickListener v) -> {
            AsyncTaskRunner runner = new AsyncTaskRunner(MainActivity.this);
            final String sleepTime = time.getText().toString();
            runner.execute(sleepTime);
        });*/
    }
}
