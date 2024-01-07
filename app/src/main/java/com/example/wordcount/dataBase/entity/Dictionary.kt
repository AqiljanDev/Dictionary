package com.example.wordcount.dataBase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class Dictionary(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "word_count")
    val wordCount: Int
)
