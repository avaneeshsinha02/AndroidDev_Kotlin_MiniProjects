package com.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return try {
            Log.d("WorkManagerTag", "MyWorker: Work is being done!")
            Result.success()
        } catch (exception: Exception) {
            Log.e("WorkManagerTag", "MyWorker: Work failed due to exception: ${exception.message}")
            Result.retry()
        }
    }
}
