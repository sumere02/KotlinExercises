package com.sumere.workmanagerjava;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RefreshDatabase extends Worker {

    private final Context myContext;

    public RefreshDatabase(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.myContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        int increaseRate = data.getInt("IncrementValue",0);
        refreshDatabase(increaseRate);
        return Result.success();
    }

    private void refreshDatabase(int increaseRate){
        SharedPreferences sharedPreferences = myContext.getSharedPreferences("com.sumere.workmanagerjava",Context.MODE_PRIVATE);
        int savedNumber = sharedPreferences.getInt("Number",0);
        savedNumber += increaseRate;
        System.out.println("Saved Number: " + savedNumber);
        sharedPreferences.edit().putInt("Number",savedNumber).apply();
    }
}
