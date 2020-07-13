package com.example.orangesnote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.example.orangesnote.data.NoteAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val notes = mutableListOf("第一个note", "第二个note", "第三个note", "第四个note")

    val noteList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initNote()
        val layoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = layoutManager
        val adpter = NoteAdapter(this, noteList)
        recyclerView.adapter = adpter
        //悬浮按钮编辑创建
        addNote.setOnClickListener {

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

    private  fun initNote(){
        noteList.clear()
        repeat(10){
            val index = (0 until notes.size).random()
            noteList.add(notes[index])
        }
    }
}