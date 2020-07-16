package com.example.orangesnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_read.*

class ReadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        val title = intent.getStringExtra("note_title") ?: ""
        val content = intent.getStringExtra("note_content") ?: ""
        val id = intent.getStringExtra("note_Id") ?: ""
        Log.d("fuck", title)
        Log.d("fuck", content)
        Log.d("fuck", id)
        readTitle.text = title
        readContent.text = content
        backBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}