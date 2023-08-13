package com.sumere.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private TextView label;
    private int counter;
    private Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        label = findViewById(R.id.label);
        start = findViewById(R.id.startButton);
        counter = 0;
    }
    public void startTimer(View view){
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                label.setText("Time: " + counter);
                counter++;
                label.setText("Time: " + counter);
                handler.postDelayed(this,1000);
            }
        };
        handler.post(runnable);
        start.setEnabled(false);
    }
    public void resetTimer(View view){
        counter = 0;
        label.setText("Time: " + counter);
    }
    public void stopTimer(View view){
        start.setEnabled(true);
        handler.removeCallbacks(runnable);
    }
}