package com.example.workmanager

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context,workerParameters: WorkerParameters): Worker(context,workerParameters) {
    companion object{
        const val CHANNEL_ID="channel_id"
        const val NOTIFICATION=1
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        showNotification()
        return Result.success()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(){
        val intent= Intent(applicationContext,MainActivity::class.java).apply{
            flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent=PendingIntent.getActivity(applicationContext,0,intent,
            PendingIntent.FLAG_IMMUTABLE)
        val notification= Notification.Builder(applicationContext,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Уведомление")
            .setContentText("Не забудь сделать ДЗ")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channelName="channel name"
            val channelDescription="channel Description"
            val channelImpotence=NotificationManager.IMPORTANCE_HIGH
            val channel= NotificationChannel(CHANNEL_ID,channelName,channelImpotence).apply{
                description=channelDescription
            }
            val notificationManage=applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManage.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(applicationContext)){
            notify(NOTIFICATION,notification.build())
        }

    }
}