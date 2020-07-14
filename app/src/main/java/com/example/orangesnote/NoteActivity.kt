package com.example.orangesnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.orangesnote.data.AppDatabase
import kotlinx.android.synthetic.main.activity_note.*
import kotlin.concurrent.thread
class NoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        val noteDao = AppDatabase.getDatabase(this).noteDao()
        submit.setOnClickListener {
            if (titleAdd.text != null){
                thread {
                    noteDao.insertNote(Note(titleAdd.text.toString(), contentAdd.text.toString() ))
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}