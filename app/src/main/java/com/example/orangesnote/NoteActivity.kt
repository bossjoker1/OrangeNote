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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.orangesnote.data.AppDatabase
import kotlinx.android.synthetic.main.activity_note.*
import java.util.*
import kotlin.concurrent.thread


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
                MainActivity.instance?.finish();
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                val request = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
                WorkManager.getInstance(this).enqueue(request)
            }

        }
        //接收广播

       /* val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("time", "Time", NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
*/
        setBtn.setOnClickListener { view ->
            val c = Calendar.getInstance()
            //调整为中国时区，不然有8小时差比较麻烦
            val tz = TimeZone.getTimeZone("Asia/Shanghai")
            c.timeZone = tz
            //获取当前时间
            if (setHour.text.toString()!=""&&setMin.text.toString()!="") {
                c.set(Calendar.HOUR_OF_DAY, setHour.text.toString().toInt());//小时
                c.set(
                    Calendar.MINUTE, setMin.text.toString().toInt()
                );//分钟
                c.set(Calendar.SECOND, 0);//秒
            }
            //计时发送通知
            val mIntent = Intent(this, MyReceiver::class.java)
            mIntent.action = "NOTIFICATION";
            val mPendingIntent =
                PendingIntent.getBroadcast(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            am = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (setHour.text.toString()==""||setMin.text.toString()==""||
                setHour.text.toString().toInt() > 24 || setMin.text.toString().toInt() > 60) {
                Toast.makeText(this, "请输入正确的时间格式！", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("fuck10", c.timeInMillis.toString())
                am!!.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, c.timeInMillis,
                    mPendingIntent
                )
                Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show()
            }
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

