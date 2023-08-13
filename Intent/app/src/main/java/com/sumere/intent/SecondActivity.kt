package com.sumere.intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sumere.intent.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val intentFrom = getIntent()
        val intentFrom = intent
        val name = intentFrom.getStringExtra("username")
        binding.nameTextViewSecondActivity.text = name
        //finish() Destroying Activity
    }
}