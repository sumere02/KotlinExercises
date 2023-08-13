package com.sumere.landmarkbook.view

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumere.landmarkbook.model.Landmark
import com.sumere.landmarkbook.R
import com.sumere.landmarkbook.adapter.LandmarkAdapter
import com.sumere.landmarkbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var landmarkList: ArrayList<Landmark>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        landmarkList = ArrayList<Landmark>()

        //Data
        landmarkList.add(Landmark("Pisa","Italy", R.drawable.pisatower))
        landmarkList.add(Landmark("Colosseum","Italy", R.drawable.collesium))
        landmarkList.add(Landmark("Eiffel","France", R.drawable.eifel))
        landmarkList.add(Landmark("London Bridge","United Kingdom", R.drawable.londonbridge))

        binding.recyclerListView.layoutManager = LinearLayoutManager(this)
        val landmarkAdapter = LandmarkAdapter(landmarkList)
        binding.recyclerListView.adapter = landmarkAdapter

    }
}