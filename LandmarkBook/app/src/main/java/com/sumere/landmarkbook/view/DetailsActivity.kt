package com.sumere.landmarkbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sumere.landmarkbook.databinding.ActivityDetailsBinding
import com.sumere.landmarkbook.model.Landmark
import com.sumere.landmarkbook.model.mySingleton

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        val intentFrom = intent
        val landmark = mySingleton.chosenLandmark
        if (landmark != null) {
            binding.landmarkNameLabelView.text = landmark!!.name
            binding.landmarkNameLabelView2.text = landmark!!.country
            binding.landmarkImageView.setImageResource(landmark!!.image)

        }
    }
}