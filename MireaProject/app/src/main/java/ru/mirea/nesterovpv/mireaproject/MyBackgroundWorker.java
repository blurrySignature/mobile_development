package ru.mirea.nesterovpv.mireaproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyBackgroundWorker extends Worker {

    public MyBackgroundWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("MyBackgroundWorker", "Выполняется фоновая задача...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            return Result.failure();
        }
        Log.d("MyBackgroundWorker", "Фоновая задача завершена.");
        return Result.success();
    }
}