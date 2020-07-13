package com.example.orangesnote.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.orangesnote.Note
import com.example.orangesnote.R
//配置note适配器
class NoteAdapter(val context: Context, val noteList: List<String>):
         RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val noteTitle:TextView = view.findViewById(R.id.noteTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //这几个参数没搞太清楚，书上只说这么用
        val view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.noteTitle.text = note
    }

    override fun getItemCount() = noteList.size
}