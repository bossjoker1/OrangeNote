package com.example.orangesnote

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.orangesnote.data.AppDatabase
import kotlinx.android.synthetic.main.activity_note.*
import java.util.*
import kotlin.concurrent.thread
import kotlin.math.abs


class NoteActivity : AppCompatActivity(){

    private var am: AlarmManager? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        val title = intent.getStringExtra("note_title") ?: ""
        val content = intent.getStringExtra("note_content") ?: ""
        val id = intent.getStringExtra("note_Id") ?: ""
        Log.d("fuck", title)
        Log.d("fuck", content)
        Log.d("fuck", id)
        titleAdd.setText(title)
        contentAdd.setText(content)
        val noteDao = AppDatabase.getDatabase(this).noteDao()
        submit.setOnClickListener {
            if (titleAdd.text.toString() != "") {
                if (title != "") {
                    thread {
                        val tempNote = noteDao.loadById(id.toLong())
                        tempNote.title = titleAdd.text.toString()
                        tempNote.content = contentAdd.text.toString()
                        noteDao.updateNote(tempNote)
                        Log.d("fuck0", tempNote.title + ", " + tempNote.content)
                    }
                } else {
                    thread {
                        noteDao.insertNote(
                            Note(
                                titleAdd.text.toString(),
                                contentAdd.text.toString()
                            )
                        )
                    }
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("time", "Time", NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)

        setBtn.setOnClickListener { view ->
            val c = Calendar.getInstance()
            val tz = TimeZone.getTimeZone("Asia/Shanghai")
            c.setTimeZone(tz)
            val hour = c?.get(Calendar.HOUR)
            val minute = c?.get(Calendar.MINUTE);
            val second = c?.get(Calendar.SECOND)
            val mIntent = Intent(this, MyReceiver::class.java)
            val mPendingIntent =
                PendingIntent.getBroadcast(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            am = this
                .getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (hour != null && minute != null) {
                var interval = abs(
                    3600 * (setHour.text.toString().toInt() - hour)
                            + 60 * (setMin.text.toString().toInt() - minute)
                ).toLong()
                Log.d("fuck11", (setMin.text.toString().toInt() - minute).toString()+","+setMin.text.toString())
                am!!.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval*1000,
                    mPendingIntent
                )
                Log.d("fuck8", hour.toString() + "," + minute.toString() + "," + interval.toString())
            }
            /**本来想做个通知的,但是通知好像不如广播来的方便
             * setBtn.setOnClickListener {
            val notification = NotificationCompat.Builder(this, "time")
            .setContentTitle("Time is up!")
            .setContentText("Task: ${titleAdd.text.toString()} ")
            .setSmallIcon(R.drawable.small_icon)
            .setLargeIcon(BitmapFactory.decodeResource(resources,
            R.drawable.large_icon))
            .build()
            runBlocking {
            launch {
            manager.notify(1, notification)
            }
            }
            }*/
        }
    }
   //监听并防止用户按back键直接返回到桌面，之前那个方法不行
    override fun onBackPressed() {
        if(titleAdd.text.toString() == ""){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
         super.onBackPressed()
    }
}

