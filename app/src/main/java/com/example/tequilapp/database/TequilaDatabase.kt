package com.example.tequilapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bar::class], version = 1, exportSchema = false)
abstract class TequilaDatabase : RoomDatabase() {
    abstract val tequilaDatabaseDao: TequilaDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: TequilaDatabase? = null

        fun getInstance(context: Context) : TequilaDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance =
                        Room.databaseBuilder(context.applicationContext, TequilaDatabase::class.java, "tequila_database")
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return instance
            }
        }
    }
}