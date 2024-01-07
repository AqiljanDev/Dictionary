package com.example.wordcount.dataBase

import android.app.Application
import androidx.room.Database
import androidx.room.Room

class App : Application() {

    val db: AppDataBase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "db"
        ).build()
    }

}