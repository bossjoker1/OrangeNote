package com.example.orangesnote.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.orangesnote.Note

@Database(version = 1, entities = [Note::class])
abstract class AppDatabase: RoomDatabase(){

    abstract fun noteDao() : NoteDao

    companion object{
        //访问实例
        private var instance : AppDatabase? = null

        @Synchronized//同步化
        fun getDatabase(context: Context):AppDatabase{
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java, "app_database")
                .build().apply {
                    instance = this
                }
        }
    }
}