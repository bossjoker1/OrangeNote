package com.example.orangesnote

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.orangesnote.data.AppDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    //定义全局变量，实现在另一个Activity中删除MainActivity
    companion object{
        var instance: MainActivity? = null
    }

    val noteList = ArrayList<Note>()

    private val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMediaPlayer()
        instance = this
        Log.d("MainActivity1", "onCreate")
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
    //还没写功能,可添加音乐啥的，应该跟天气预报的引入差不多
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.backup -> if (!mediaPlayer.isPlaying){
                    mediaPlayer.start()
                }
            R.id.delete -> if (mediaPlayer.isPlaying){
                mediaPlayer.pause()
                mediaPlayer.reset()
                initMediaPlayer()
            }
        }
        return true
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity1", "onPau")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity1", "onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity1", "onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity1", "onDestroy")
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    //不知道咋刷新，只能在onRestart上重新显示，估计效率很低,原来没啥用，还造成了bug，心累
    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity1", "onRestart")
       }

    private fun initMediaPlayer(){
        val assetManager = assets
        val fd = assetManager.openFd("Versions.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
        mediaPlayer.prepare()
    }

}