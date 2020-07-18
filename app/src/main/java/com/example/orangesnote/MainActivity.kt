package com.example.orangesnote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
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
       // noteList.add(Note("第一个示例", "内容"))
        thread {
            for (note in noteDao.loadAllNotes())
                noteList.add(note)
            for (note in noteDao.loadAllNotes()){
                Log.d("fuck1", note.title+" , "+note.content +", "+note.id)
            }
        }
        val layoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = layoutManager
        val adpter = NoteAdapter(this, noteList)
        recyclerView.adapter = adpter
        //实现滑动与拖拽
        val callback: ItemTouchHelper.Callback = RecycleItemTouchHelper(adpter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //悬浮按钮编辑创建
        addNote.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
            finish()
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

    override fun onPause() {
        super.onPause()
        val noteDao = AppDatabase.getDatabase(this).noteDao()
        /**
        thread {
        for (i in noteList){
        Log.d("fuck7", i.title)
        noteDao.updateNote(i)
        }
        }
        */
    }

    //不知道咋刷新，只能在onRestart上重新显示，估计效率很低
    override fun onRestart() {
        super.onRestart()
        val noteDao = AppDatabase.getDatabase(this).noteDao()
        noteList.clear()
        thread {
            for (note in noteDao.loadAllNotes())
                noteList.add(note)
        }
        val layoutManager = GridLayoutManager(this, 1)
        recyclerView.layoutManager = layoutManager
        val adpter = NoteAdapter(this, noteList)
        recyclerView.adapter = adpter
    }

}