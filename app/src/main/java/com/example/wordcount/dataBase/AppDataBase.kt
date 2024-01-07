package com.example.wordcount.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wordcount.dataBase.entity.Dictionary

@Database(
    entities = [
        Dictionary::class
    ],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getDictionaryDAO(): DictionaryDAO
}