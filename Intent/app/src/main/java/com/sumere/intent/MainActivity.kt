package com.sumere.intent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sumere.intent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun nextActivity(view: View){
        var name = binding.nameTextViewMainActivity.text.toString()
        val intent = Intent(this@MainActivity,SecondActivity::class.java)
        intent.putExtra("username",name)
        this.startActivity(intent)
    }
}