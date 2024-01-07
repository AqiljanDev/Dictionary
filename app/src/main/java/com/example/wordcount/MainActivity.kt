package com.example.wordcount

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wordcount.viewModel.DictionaryFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, DictionaryFragment())
                .commit()
        }

    }
}