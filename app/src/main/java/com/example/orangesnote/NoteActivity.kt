package com.example.orangesnote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.orangesnote.data.AppDatabase
import kotlinx.android.synthetic.main.activity_note.*
import kotlin.concurrent.thread


class NoteActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        val noteDao = AppDatabase.getDatabase(this).noteDao()
        submit.setOnClickListener {
            if (titleAdd.text.toString() != "") {
                val intent = Intent(this, MainActivity::class.java)
                thread {
                    noteDao.insertNote(Note(titleAdd.text.toString(), contentAdd.text.toString()))
                }
                startActivity(intent)
                finish()
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