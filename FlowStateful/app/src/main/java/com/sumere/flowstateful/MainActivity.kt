package com.sumere.flowstateful

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sumere.flowstateful.ui.theme.FlowStatefulTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowStatefulTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "firstScreen"){
                        composable(route = "firstScreen"){
                            val viewModel = viewModel<FirstScreenViewModel>()
                            //val time = viewModel.counter.collectAsState()
                            val time = viewModel.counter.collectAsStateWithLifecycle() //Lifecycle aware
                            FirstScreen(time = time.value) {
                                navController.navigate("secondScreen")
                            }
                        }
                        composable(route = "secondScreen"){
                            SecondScreen()
                        }
                    }
                }
            }
        }
    }
}

