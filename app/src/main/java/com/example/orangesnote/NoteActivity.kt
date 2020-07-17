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
                if (title!=""){
                    thread {
                        val tempNote = noteDao.loadById(id.toLong())
                        tempNote.title = titleAdd.text.toString()
                        tempNote.content = contentAdd.text.toString()
                        noteDao.updateNote(tempNote)
                        Log.d("fuck0", tempNote.title+", "+tempNote.content)
                    }
                }else {
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