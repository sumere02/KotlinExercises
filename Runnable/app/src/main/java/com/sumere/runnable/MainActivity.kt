package com.sumere.runnable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.sumere.runnable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var number = 0
    //private var runnable : Runnable = Runnable{}
    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
    }

    fun startTimer(view: View){
        number = 0
        runnable = object: Runnable{
            override fun run() {
                number += 1
                binding.timeLabelView.text = "Time: ${number}"
                handler.postDelayed(runnable,1000)
            }
        }
        handler.post(runnable)
        binding.startButtonView.isEnabled = false
    }

    fun stopTimer(view: View){
        binding.startButtonView.isEnabled = true
        handler.removeCallbacks(runnable)
    }
}