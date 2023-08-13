package com.sumere.kotlincryptocrazy.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumere.kotlincryptocrazy.model.CryptoModel
import com.sumere.kotlincryptocrazy.repository.CryptoDownload
import com.sumere.kotlincryptocrazy.service.CryptoAPI
import com.sumere.kotlincryptocrazy.util.Resource
import com.sumere.kotlincryptocrazy.view.RecyclerViewAdapter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoViewModel(private val cryptoDownloadRepository: CryptoDownload): ViewModel() {

    val cryptoList = MutableLiveData<Resource<List<CryptoModel>>>()
    val cryptoError = MutableLiveData<Resource<Boolean>>()
    val cryptoLoading = MutableLiveData<Resource<Boolean>>()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        //println(throwable.localizedMessage)
        cryptoError.value = Resource.error(throwable.localizedMessage ?: "Error",true)
    }

    fun getDataFromAPI(){
        cryptoLoading.value = Resource.loading(data = true)
        viewModelScope.launch (Dispatchers.IO + exceptionHandler){
            val resource = cryptoDownloadRepository.downloadCryptos()
            withContext(Dispatchers.Main){
                resource.data?.let {
                    cryptoList.value = resource
                    cryptoLoading.value = Resource.loading(false)
                    cryptoError.value = Resource.error("",false)
                }
            }
        }
    }
}