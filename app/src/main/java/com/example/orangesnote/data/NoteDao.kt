package com.example.orangesnote.data

import androidx.room.*
import com.example.orangesnote.Note

@Dao
interface NoteDao {


    @Update
    fun updateNote(newNote: Note)

    @Query("select * from Note")
    fun loadAllNotes(): List<Note>

    @Query("select * from Note where title > :title")
    fun loadNotesLongerThan(title:String) : List<Note>

    @Query("select * from Note where id == :id")
    fun loadById(id:Long) :Note

    @Delete
    fun deleteNote(note: Note)

    @Query("delete from Note where title == :title")
    fun deleteNoteByTitle(title: String): Int

    @Insert
    fun insertNote(note: Note)


}