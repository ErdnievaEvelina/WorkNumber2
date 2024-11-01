package com.example.workmanager

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myPeriodWork()
    }

    fun myPeriodWork(){
        val constraints=Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()
        val myWorkRequest= PeriodicWorkRequest.Builder(MyWorker::class.java,
            1, TimeUnit.DAYS).setConstraints(constraints).addTag("mu_id").build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("my_id",ExistingPeriodicWorkPolicy.KEEP,myWorkRequest)

    }
}