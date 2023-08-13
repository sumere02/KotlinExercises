package com.sumere.recyclerviewlandmark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sumere.recyclerviewlandmark.databinding.ActivityLandmarkBinding;

import java.util.zip.Inflater;

public class LandmarkActivity extends AppCompatActivity {
    private ActivityLandmarkBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandmarkBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        Landmark landmark = (Landmark) intent.getSerializableExtra("Landmark");
        /*
        * Singleton singleton = Singleton.getInstance();
        * Landmark selectedLandmark = singleton.getSentLandmark();
        * */
        binding.countryLabel.setText(landmark.getCountry());
        binding.nameLabel.setText(landmark.getName());
        binding.landmarkImage.setImageResource(landmark.getImageView());
    }
}