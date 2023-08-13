package com.sumere.kotlincryptocrazy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sumere.kotlincryptocrazy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
    }
}