package com.sumere.fragmentkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sumere.fragmentkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun firstFragmentButtonClicked(view: View){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val firstFragment = BlankFragment()
        fragmentTransaction.replace(R.id.frameLayoutViewMainActivity,firstFragment).commit()
    }

    fun secondFragmentButtonClicked(view: View){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val secondFragment = BlankFragment2()
        fragmentTransaction.replace(R.id.frameLayoutViewMainActivity,secondFragment).commit()
    }
}