package com.sumere.multipleactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    String name = "";
    EditText nameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameText = findViewById(R.id.nameTextA1);
    }
    public void changeActivity(View view){
        name = nameText.getText().toString();
        Intent intent = new Intent(this,MainActivity2.class);
        intent.putExtra("Input",name);
        startActivity(intent);
    }
}