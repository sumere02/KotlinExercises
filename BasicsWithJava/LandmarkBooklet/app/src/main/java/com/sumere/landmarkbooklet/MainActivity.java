package com.sumere.landmarkbooklet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.sumere.landmarkbooklet.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ArrayList<Landmark> landmarkArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        landmarkArrayList = new ArrayList<>();
        landmarkArrayList.add(new Landmark("Pisa Tower","Italy",R.drawable.pisatower));
        landmarkArrayList.add(new Landmark("Eiffel Tower","France",R.drawable.eifel));
        landmarkArrayList.add(new Landmark("Colosseum","Italy",R.drawable.collesium));
        landmarkArrayList.add(new Landmark("London Bridge","United Kingdom",R.drawable.londonbridge));

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                landmarkArrayList.stream().map(landmark -> landmarkArrayList.get(landmarkArrayList.indexOf(landmark)).getName()).collect(Collectors.toList())
        );
        binding.listView.setAdapter(arrayAdapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("Landmark",landmarkArrayList.get(i));
                startActivity(intent);
            }
        });
    }


}