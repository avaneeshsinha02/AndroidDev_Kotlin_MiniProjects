package com.example.workmanager

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var oneTimeWorkRequestId: java.util.UUID? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scheduleOneTimeButton: Button = findViewById(R.id.scheduleOneTimeButton)
        val schedulePeriodicButton: Button = findViewById(R.id.schedulePeriodicButton)
        val cancelAllWorkButton: Button = findViewById(R.id.cancelAllWorkButton)

        scheduleOneTimeButton.setOnClickListener {
            scheduleOneTimeWork()
        }

        schedulePeriodicButton.setOnClickListener {
            schedulePeriodicWork()
        }

        cancelAllWorkButton.setOnClickListener {
            cancelAllWork()
        }
    }

    private fun scheduleOneTimeWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setConstraints(constraints)
            .build()

        oneTimeWorkRequestId = oneTimeWorkRequest.id

        WorkManager.getInstance(applicationContext).enqueue(oneTimeWorkRequest)
        Log.d("WorkManagerTag", "One-time work enqueued with ID: $oneTimeWorkRequestId")

        observeWorkStatus(oneTimeWorkRequestId!!)
    }

    private fun schedulePeriodicWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            MyWorker::class.java,
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .addTag("periodic_task_tag")
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                "MyPeriodicWork",
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
        Log.d("WorkManagerTag", "Periodic work enqueued with unique name: MyPeriodicWork")
    }

    private fun observeWorkStatus(workId: java.util.UUID) {
        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(workId)
            .observe(this) { workInfo ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.ENQUEUED -> Log.d("WorkManagerTag", "Work (ID: $workId) Enqueued")
                        WorkInfo.State.RUNNING -> Log.d("WorkManagerTag", "Work (ID: $workId) Running")
                        WorkInfo.State.SUCCEEDED -> Log.d("WorkManagerTag", "Work (ID: $workId) Succeeded")
                        WorkInfo.State.FAILED -> Log.d("WorkManagerTag", "Work (ID: $workId) Failed")
                        WorkInfo.State.BLOCKED -> Log.d("WorkManagerTag", "Work (ID: $workId) Blocked")
                        WorkInfo.State.CANCELLED -> Log.d("WorkManagerTag", "Work (ID: $workId) Cancelled")
                    }
                }
            }
    }

    private fun cancelAllWork() {
        WorkManager.getInstance(applicationContext).cancelAllWork()
        Log.d("WorkManagerTag", "All WorkManager tasks cancelled.")
    }
}
