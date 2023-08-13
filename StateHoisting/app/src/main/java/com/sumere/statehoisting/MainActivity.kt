package com.sumere.statehoisting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sumere.statehoisting.ui.theme.StateHoistingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(){
    val myString = remember{mutableStateOf("Test")}
    Surface (color = Color.DarkGray,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            SpecialText(string = "Test")
            Spacer(modifier = Modifier.padding(10.dp))
            SpecialText(string = "Android")
            Spacer(modifier = Modifier.padding(10.dp))
            SpecialTextField(myString.value){
                myString.value = it
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialTextField(myString: String,function: (String) -> Unit){
    TextField(
        value = myString,
        onValueChange = function
    )
}

@Composable
fun SpecialText(string: String){
    Text(
        text = string,
        fontSize = 24.sp,
        color = Color.White,
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Monospace
    )
}

@Preview(showBackground = true)
@Composable
fun FirstPreview() {
    MainScreen()
}