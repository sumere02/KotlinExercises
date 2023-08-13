package com.sumere.artbookfragments.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.Navigation
import com.sumere.artbookfragments.R
import com.sumere.artbookfragments.databinding.ActivityMainBinding
import com.sumere.artbookfragments.databinding.FragmentMainPageBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.mainMenuAddArtButtonView){
            val action = MainPageFragmentDirections.actionMainPageFragmentToArtDetailsFragment(true,null)
            Navigation.findNavController(this,R.id.fragmentContainerViewMainActivity).navigate(action)
        }
        return super.onOptionsItemSelected(item)

    }
}