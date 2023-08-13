package com.sumere.cryptocrazy.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumere.cryptocrazy.model.Crypto
import com.sumere.cryptocrazy.model.CryptoItem
import com.sumere.cryptocrazy.repository.CryptoRepository
import com.sumere.cryptocrazy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(private val repository: CryptoRepository): ViewModel(){
    var cryptoList = mutableStateOf<List<Crypto>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    private var initialCryptoList = listOf<Crypto>()
    private var isSearchStarting = true

    init{
        loadCryptos()
    }

    fun searchCryptoList(query: String){
        val listToSearch = if(isSearchStarting){
            cryptoList.value
        } else{
            initialCryptoList
        }
        viewModelScope.launch(Dispatchers.Default){
            if(query.isEmpty()){
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            } else {
                val results = listToSearch.filter {
                    it.currency.contains(query.trim(),true)
                }
                if(isSearchStarting){
                    initialCryptoList = cryptoList.value
                    isSearchStarting = false
                }
                cryptoList.value = results
            }
        }
    }


    fun loadCryptos(){
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.getCryptoList()
            when(result) {
                is Resource.Success ->{
                    val cryptos = result.data!!.mapIndexed {index, crypto ->
                        Crypto(crypto.currency,crypto.price)
                    } as List<Crypto>
                    errorMessage.value = ""
                    isLoading.value = false
                    cryptoList.value = cryptos
                }
                is Resource.Loading ->{}
                is Resource.Error ->{
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }

            }
        }

    }
}