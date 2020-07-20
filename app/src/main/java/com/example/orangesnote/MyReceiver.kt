package com.example.orangesnote


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


class MyReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        if (intent.action == "NOTIFICATION") {
            val manager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel("time", "Time",
                 NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
            val notification = NotificationCompat.Builder(context, "time")
                .setContentTitle("Time is up!")
                .setContentText("You have a task to do! ")
                .setSmallIcon(R.drawable.small_icon)
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.large_icon)
                .build()
                    manager.notify(1, notification)
        }
    }
}



