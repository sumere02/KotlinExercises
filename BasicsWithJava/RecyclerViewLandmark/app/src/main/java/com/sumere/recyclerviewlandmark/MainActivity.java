package com.sumere.recyclerviewlandmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.sumere.recyclerviewlandmark.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Landmark> landmarkArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        landmarkArrayList = new ArrayList<>();
        landmarkArrayList.add(new Landmark("Pisa Tower","Italy",R.drawable.pisatower));
        landmarkArrayList.add(new Landmark("Eiffel Tower","France",R.drawable.eifel));
        landmarkArrayList.add(new Landmark("Collesium","Italy",R.drawable.collesium));
        landmarkArrayList.add(new Landmark("London Bridge","United Kingdom",R.drawable.londonbridge));
        binding.landmarkList.setLayoutManager(new LinearLayoutManager(this));
        LandmarkAdapter landmarkAdapter = new LandmarkAdapter(landmarkArrayList);
        binding.landmarkList.setAdapter(landmarkAdapter);
    }
}