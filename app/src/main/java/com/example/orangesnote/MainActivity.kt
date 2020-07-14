package com.example.orangesnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.example.orangesnote.data.AppDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {


    val noteList = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val noteDao = AppDatabase.getDatabase(this).noteDao()
        noteList.clear()
        noteList.add(Note("第一个示例", "内容"))
        thread {
            noteDao.deleteNoteByTitle("第二行")
            for (note in noteDao.loadAllNotes())
                noteList.add(note)
        }
        val layoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = layoutManager
        val adpter = NoteAdapter(this, noteList)
        recyclerView.adapter = adpter
        //悬浮按钮编辑创建
        addNote.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }
    }
    //添加toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }
    //还没写功能
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

}