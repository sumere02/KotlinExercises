package com.sumere.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText num1Text;
    EditText num2Text;
    EditText resultText;

    public void calculate(String operator){
        switch(operator){
            case "+":
                break;
            case "-":
                break;
            case "*":
                break;
            case "/":
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num1Text = findViewById(R.id.number1Text);
        num2Text = findViewById(R.id.number2Text);
        resultText = findViewById(R.id.resultText);
    }

    public void add(View view){
        double num1 = Double.parseDouble(num1Text.getText().toString());
        double num2 = Double.parseDouble(num2Text.getText().toString());
        double result = num1 + num2;
        resultText.setText("Result: " + result);
    }

    public void subtract(View view){
        double num1 = Double.parseDouble(num1Text.getText().toString());
        double num2 = Double.parseDouble(num2Text.getText().toString());
        double result = num1 - num2;
        resultText.setText("Result: " + result);
    }

    public void multiply(View view){
        double num1 = Double.parseDouble(num1Text.getText().toString());
        double num2 = Double.parseDouble(num2Text.getText().toString());
        double result = num1 * num2;
        resultText.setText("Result: " + result);
    }

    public void divide(View view){
        double num1 = Double.parseDouble(num1Text.getText().toString());
        double num2 = Double.parseDouble(num2Text.getText().toString());
        double result;
        if(num2 != 0)
            result = num1 / num2;
        else
            result = 0;
        resultText.setText("Result: " + result);
    }
}