package com.example.orangesnote

import android.app.KeyguardManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
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
                 NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
            val mintent = Intent(context, MainActivity::class.java)
            val pi = PendingIntent.getActivity(context, 0, mintent, 0)
            val notification = NotificationCompat.Builder(context, "time")
                .setContentTitle("Time is up!")
                .setContentText("You have a task to do! ")
                .setSmallIcon(R.drawable.small_icon)
                .setContentIntent(pi)
                .setAutoCancel(true)
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.large_icon)
                .build()
                    manager.notify(1, notification)

            val km =
                context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            if (km.isKeyguardLocked) {   //为true就是锁屏状态下
                //启动Activity
                val alarmIntent = Intent(context, MainActivity::class.java)
                //携带数据
                //activity需要新的任务栈
                alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(alarmIntent)
            }
        }
    }
}



