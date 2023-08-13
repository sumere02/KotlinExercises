package com.sumere.catchthegame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int score;
    private boolean flag;
    private TextView timerText;
    private TextView scoreText;
    private Button start;
    private ImageView[] imageArray;
    private Handler handler;
    private Runnable runnable;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score = 0;
        flag = false;
        timerText = findViewById(R.id.timerLabel);
        scoreText = findViewById(R.id.scoreLabel);
        start = findViewById(R.id.startButton);
        start.setEnabled(true);
        handler = new Handler();
        imageArray = new ImageView[9];
        imageArray[0] = findViewById(R.id.imageView);
        imageArray[1] = findViewById(R.id.imageView2);
        imageArray[2] = findViewById(R.id.imageView3);
        imageArray[3] = findViewById(R.id.imageView4);
        imageArray[4] = findViewById(R.id.imageView5);
        imageArray[5] = findViewById(R.id.imageView6);
        imageArray[6] = findViewById(R.id.imageView7);
        imageArray[7] = findViewById(R.id.imageView8);
        imageArray[8] = findViewById(R.id.imageView9);
        for(ImageView image : imageArray)
            image.setVisibility(View.INVISIBLE);
        random = new Random(0);
    }

    public void addScore(View view){
        score++;
        scoreText.setText("Score: " + score);
    }
    public void startGame(View view){
        start.setEnabled(false);
        runnable = new Runnable() {
            @Override
            public void run() {
                for(ImageView image:imageArray)
                    image.setVisibility(View.INVISIBLE);
                imageArray[random.nextInt(9)].setVisibility(View.VISIBLE);
                handler.postDelayed(this,500);
            }
        };
        handler.post(runnable);
        /*
        //Animation
        TranslateAnimation mAnimation = new TranslateAnimation(0,245,30,800);
        mAnimation.setDuration(1000);
        mAnimation.setFillAfter(true);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        duck.setAnimation(mAnimation);
        */
        new CountDownTimer(10000,1000){
            @Override
            public void onTick(long l) {
                String temp = "Timer: " + l/1000;
                timerText.setText(temp);
            }

            @Override
            public void onFinish() {
                String temp = "Timer: 0";
                timerText.setText(temp);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Game Over");
                alert.setMessage("Score: " + score + "\nTry Again");
                alert.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        flag = true;
                        restartGame();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        flag = false;
                        restartGame();
                    }
                });
                alert.show();
            }
        }.start();
    }

    public void restartGame() {
        String temp;
        score = 0;
        temp = "Score: " + score;
        scoreText.setText(temp);
        temp = "Timer: " + 100;
        timerText.setText(temp);
        if (flag) {
            start.performClick();
        } else {
            start.setEnabled(true);
            for(ImageView image:imageArray)
                image.setVisibility(View.INVISIBLE);
            handler.removeCallbacks(runnable);
        }
    }
}