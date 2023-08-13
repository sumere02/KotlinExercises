package com.sumere.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var timerLabel = findViewById<TextView>(R.id.timerLabelView)
        object : CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                timerLabel.text = "Remaining Time: ${p0 / 1000}"
            }

            override fun onFinish() {
                timerLabel.text = "Congratulations"
            }
        }.start()
    }
}