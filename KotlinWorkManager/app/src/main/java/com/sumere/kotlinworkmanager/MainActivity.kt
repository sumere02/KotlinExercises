package com.sumere.kotlinworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Data.Builder().putInt("intKey",1).build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()


        val myWorkRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraints)
            .setInputData(data)
            //.setInitialDelay(15,TimeUnit.MINUTES)
            .build()
        //One Time Work Manager
        WorkManager.getInstance(this@MainActivity).enqueue(myWorkRequest)

        val myPeriodicWorkRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDatabase>(15,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(data)
            .build()
        //Periodic Work Manager
        WorkManager.getInstance(this@MainActivity).enqueue(myPeriodicWorkRequest)

        WorkManager.getInstance(this@MainActivity).getWorkInfoByIdLiveData(myPeriodicWorkRequest.id).observe(this@MainActivity,
            Observer {
                if (it.state == WorkInfo.State.RUNNING){
                    println("Running")
                } else if(it.state == WorkInfo.State.FAILED){
                    println("Failed")
                } else if(it.state == WorkInfo.State.SUCCEEDED){
                    println("Succeeded")
                }
            })

        //Chaining
        WorkManager.getInstance(this@MainActivity)
            .beginWith(myWorkRequest)
            .then(myWorkRequest)
            .then(myWorkRequest)
            .enqueue()

    }
}