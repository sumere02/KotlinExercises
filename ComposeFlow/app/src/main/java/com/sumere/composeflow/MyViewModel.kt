package com.sumere.composeflow

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MyViewModel: ViewModel() {
    val countDownTimerFlow = flow<Int> {
        val countDownFrom = 10
        var counter = countDownFrom
        emit(countDownFrom)
        while(counter > 0){
            delay(1000)
            counter--
            emit(counter)
        }
    }
    private fun collectInViewModel(){
        /*
        viewModelScope.launch {
            countDownTimerFlow

                .filter {
                    it % 3 == 0
                }
                .map {
                    it * it
                }
                .collect{
                println("Counter: ${it}")
            }

        }

        countDownTimerFlow
            .onEach { println(it) }.launchIn(viewModelScope)
        */
    }

    //Live Data Comparisons

    private val _livedata = MutableLiveData<String>("KotlinLiveData")
    val liveData: LiveData<String> = _livedata

    fun changeLiveDataValue(){
        _livedata.value = "Live Data"
    }

    private val _stateFlow = MutableStateFlow("KotlinStateFlow")
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun changeStateFlowValue(){
        _stateFlow.value = "State Flow"
    }

    fun changeSharedFlowValue(){
        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }

    init {
        collectInViewModel()
    }

}