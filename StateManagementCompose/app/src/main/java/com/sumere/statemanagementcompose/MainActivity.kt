@file:OptIn(ExperimentalMaterial3Api::class)

package com.sumere.statemanagementcompose

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sumere.statemanagementcompose.ui.theme.StateManagementComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainFrame()
        }
    }
}


@Composable
fun MainFrame(){
    Surface (color = Color.DarkGray) {
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            val myString = remember{mutableStateOf("Android Compose")}
            val textString = remember { mutableStateOf("Hello, World!") }
            TextField(
                value = myString.value ,
                onValueChange = {
                    myString.value = it
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = textString.value,
                fontSize = 24.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    textString.value = "Hello!"
            }) {
                Text(text = "Button Test")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.kana),
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp),
                contentDescription = "Test Image"
            )
            /*
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Test Image 2"
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                painter = painterResource(id = R.drawable.kana),
                contentDescription = "Test Image 3"
            )
            */
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainFrame()
}
