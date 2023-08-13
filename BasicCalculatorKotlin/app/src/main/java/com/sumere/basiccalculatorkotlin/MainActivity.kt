package com.sumere.basiccalculatorkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sumere.basiccalculatorkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var number1 : Double? = null
    private var number2 : Double? = null
    private var result : Any = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        //val view = binding.root
        setContentView(binding.root)

    }

    private fun getNumbers(){
        number2 = binding.num2TextView.text.toString().toDoubleOrNull()
        number1 = binding.num1TextView.text.toString().toDoubleOrNull()
    }

    fun addNumbers(view : View){
        getNumbers()
        if(number1 != null && number2 != null) {
            result = number1!! + number2!!
        } else{
            result = "Invalid Input"
        }
        binding.resultLabel.text = "Result: ${result}"
    }

    fun subtractNumbers(view : View){
        getNumbers()
        if(number1 != null && number2 != null) {
            result = number1!! - number2!!
        } else{
            result = "Invalid Input"
        }
        binding.resultLabel.text = "Result: ${result}"
    }

    fun multiplyNumbers(view: View){
        getNumbers()
        if(number1 != null && number2 != null) {
            result = number1!! * number2!!
        } else{
            result = "Invalid Input"
        }
        binding.resultLabel.text = "Result: ${result}"
    }
    fun divideNumbers(view : View){
        getNumbers()
        if(number1 != null && number2 != null) {
            result = number1!! / number2!!
        } else{
            result = "Invalid Input"
        }
        binding.resultLabel.text = "Result: ${result}"
    }
}