package com.sumere.countdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView remainder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        remainder = findViewById(R.id.label);
        CountDownTimer counter = new CountDownTimer(10000,1000){

            @Override
            public void onTick(long l) {
                remainder.setText("Remaining: " + l/1000);
            }

            @Override
            public void onFinish() {
                remainder.setText("Remaining: " + 0 );
                Toast.makeText(MainActivity.this,"Process finished succesfully.",Toast.LENGTH_LONG).show();
            }
        };
        counter.start();
    }
}