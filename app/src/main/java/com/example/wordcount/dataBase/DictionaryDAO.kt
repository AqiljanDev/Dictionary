package com.example.wordcount.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.wordcount.dataBase.entity.Dictionary
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDAO {

    @Query("SELECT * FROM dictionary")
    fun getAll(): Flow<List<Dictionary>>

    @Insert
    fun insert(dictionary: Dictionary)

    @Update
    fun update(dictionary: Dictionary)

    @Query("DELETE FROM dictionary")
    fun deleteAll()
}