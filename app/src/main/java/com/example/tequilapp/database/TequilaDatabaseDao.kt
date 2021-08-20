package com.example.tequilapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TequilaDatabaseDao {
    @Insert
    fun insert(bar: Bar)

    @Update
    fun update(bar: Bar)

    @Query("SELECT * FROM bar_table WHERE barId = :key")
    fun get(key: Long): LiveData<Bar>

    @Query("SELECT * FROM bar_table ORDER BY name")
    fun getAll(): LiveData<List<Bar>>


}