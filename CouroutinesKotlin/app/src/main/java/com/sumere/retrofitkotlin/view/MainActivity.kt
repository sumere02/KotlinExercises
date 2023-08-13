package com.sumere.retrofitkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumere.retrofitkotlin.R
import com.sumere.retrofitkotlin.adapter.CryptoAdapter
import com.sumere.retrofitkotlin.databinding.ActivityMainBinding
import com.sumere.retrofitkotlin.databinding.RecyclerRowBinding
import com.sumere.retrofitkotlin.model.CryptoModel
import com.sumere.retrofitkotlin.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),CryptoAdapter.Listener  {

    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModelList: ArrayList<CryptoModel>? = null
    private lateinit var cryptoAdapter: CryptoAdapter
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this@MainActivity.layoutInflater)
        setContentView(binding.root)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerList.layoutManager = layoutManager

        loadData()
    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getData()
            withContext(Dispatchers.Main+exceptionHandler){
                if(response.isSuccessful){
                    response.body()?.let {
                        cryptoModelList = ArrayList(it)
                        cryptoModelList?.let {
                            cryptoAdapter =CryptoAdapter(cryptoModelList!!,this@MainActivity)
                            binding.recyclerList.adapter= cryptoAdapter
                        }
                    }
                }
            }
        }

    }



    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this@MainActivity,"Clicked: ${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

}