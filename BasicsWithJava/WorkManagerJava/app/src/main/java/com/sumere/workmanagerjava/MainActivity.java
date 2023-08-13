package com.sumere.workmanagerjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Data data = new Data.Builder().putInt("IncrementValue",1).build();
        /*
            Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        */
        /*
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(RefreshDatabase.class)
                //.setConstraints(constraints)
                .setInputData(data)
                //.setInitialDelay(5, TimeUnit.MINUTES)
                //.addTag("myTag")
                .build();
        WorkManager.getInstance(this).enqueue(workRequest);
        */
        WorkRequest workRequest = new PeriodicWorkRequest.Builder(RefreshDatabase.class,15,TimeUnit.MINUTES)
                .setInputData(data)
                .addTag("TempTag")
                .build();
        WorkManager.getInstance(this).enqueue(workRequest);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if(workInfo.getState() == WorkInfo.State.RUNNING){
                    System.out.println("Running");
                }
            }
        });
        //WorkManager.getInstance(this).cancelAllWork();
        //WorkManager.getInstance(this).getWorkInfosByTag("TempTag");

        //Chaining

        /*
        WorkManager.getInstance(this).beginWith(OneTimeWorkRequest).
                then(OneTimeWorkRequest)
                .then(OneTimeWorkRequest).enqueue();
        */
    }

}