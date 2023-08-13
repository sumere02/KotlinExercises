package com.sumere.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    EditText num;
    EditText result;
    SharedPreferences sharedPreferences;
    int age = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num = findViewById(R.id.ageText);
        result = findViewById(R.id.resultText);
        sharedPreferences = this.getSharedPreferences("com.sumere.sharedpreferences", Context.MODE_PRIVATE);
        age = sharedPreferences.getInt("storedAge",-1);
        if(age != 0)
            result.setText(""+age);
    }

    public void saveAge(View view){
        if(!num.getText().toString().matches("")) {
            age = Integer.parseInt(num.getText().toString());
            result.setText("" + age);
            sharedPreferences.edit().putInt("storedAge",age).apply();
        }
    }

    public void delete(View view) {
        age = sharedPreferences.getInt("storedAge", -1);
        if (age != -1) {
            sharedPreferences.edit().remove("storedAge").apply();
            result.setText("");
            num.setText("");
        }
    }
}