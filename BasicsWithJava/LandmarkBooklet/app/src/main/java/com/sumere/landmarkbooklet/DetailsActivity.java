package com.sumere.landmarkbooklet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sumere.landmarkbooklet.Landmark;
import com.sumere.landmarkbooklet.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        Landmark landmark = (Landmark)intent.getSerializableExtra("Landmark");
        binding.imageView.setImageResource(landmark.getImage());
        binding.countryLabel.setText(landmark.getCountry());
        binding.landmarkLabel.setText(landmark.getName());

    }
}