package com.example.orangesnote

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.orangesnote.data.AppDatabase
import java.util.*
import kotlin.concurrent.thread

//配置note适配器
class NoteAdapter(val context: Context, val noteList: ArrayList<Note>):
         RecyclerView.Adapter<NoteAdapter.ViewHolder>(), RecycleItemTouchHelper.ItemTouchHelperCallback{
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val noteTitle:TextView = view.findViewById(R.id.noteTitle)
        val noteContent:TextView = view.findViewById(R.id.noteContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //这几个参数没搞太清楚，书上只说这么用
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        //妄图添加点击编辑功能
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val note  = noteList[position]
            val intent = Intent(parent.context, NoteActivity::class.java).apply {
                putExtra("note_title", note.title)
                putExtra("note_content", note.content)
                putExtra("note_Id",note.id.toString())
        }
            context.startActivity(intent)
            Toast.makeText(parent.context, " you are going to edit 《${note.title}》.",
            Toast.LENGTH_SHORT).show()
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.noteTitle.text = note.title
        holder.noteContent.text = note.content
    }

    override fun getItemCount() = noteList.size
//卡了好久啊
    override fun onItemDelete(positon: Int) {
        val noteDao = AppDatabase.getDatabase(context).noteDao()
        val delnote = noteList[positon]
        noteList.remove(delnote)
        notifyItemRemoved(positon)
        thread {
            noteDao.deleteNote(delnote)
        }
        Log.d("fuck5", positon.toString()+noteList.size)
    }

    override fun onMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(noteList, fromPosition, toPosition) //交换数据
        notifyItemMoved(fromPosition, toPosition)
    }

}

