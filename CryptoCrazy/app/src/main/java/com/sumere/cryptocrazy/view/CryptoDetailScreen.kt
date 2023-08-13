package com.sumere.cryptocrazy.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.sumere.cryptocrazy.model.Crypto
import com.sumere.cryptocrazy.model.CryptoItem
import com.sumere.cryptocrazy.util.Resource
import com.sumere.cryptocrazy.viewmodel.CryptoDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun CryptoDetailScreen(id:String,price: String,navController: NavController,viewModel: CryptoDetailViewModel = hiltViewModel()){
    /*
    //Step 1 - Wrong - Don't do
    val scope = rememberCoroutineScope()
    var cryptoItem by remember{ mutableStateOf<Resource<List<CryptoItem>>>(Resource.Loading()) }
    scope.launch {
        cryptoItem = viewModel.getCrypto()
    }
    */
    /*
    //Step 2
    var cryptoItem by remember{ mutableStateOf<Resource<List<CryptoItem>>>(Resource.Loading()) }
    LaunchedEffect(key1 = Unit){
       cryptoItem = viewModel.getCrypto()
    }
    */
    val cryptoItem = produceState<Resource<List<CryptoItem>>>(initialValue = Resource.Loading()){
        value = viewModel.getCrypto()
    }.value
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.tertiary),
        contentAlignment = Alignment.Center
    )
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when(cryptoItem){
                is Resource.Success -> {
                    val selectedCrypto = cryptoItem.data!![0]
                    Text(text = selectedCrypto.name,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Image(painter = rememberAsyncImagePainter(model = selectedCrypto.logo_url),
                        contentDescription = "Coin",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp, 200.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.LightGray, CircleShape)
                    )

                    Text(text = price,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }
                is Resource.Error -> {
                    Text(text = cryptoItem.message!!)
                }
                is Resource.Loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}