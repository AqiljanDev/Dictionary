package com.example.wordcount.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.example.wordcount.dataBase.DictionaryDAO
import com.example.wordcount.dataBase.entity.Dictionary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class DictionaryViewModel(private val dictionaryDAO: DictionaryDAO) : ViewModel() {
    val dictionaryAll = this.dictionaryDAO.getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    fun onCreateDictionary(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dictionaryDAO.insert(
                Dictionary(
                    word,
                    0
                )
            )
        }
    }

    fun onUpdateWordCount(dictionary: Dictionary) {
        viewModelScope.launch(Dispatchers.IO) {
            dictionaryAll.value[
                    dictionaryAll.value.indexOf(dictionary)
            ].let {
                val db = it.copy(
                    wordCount = it.wordCount + 1
                )

                dictionaryDAO.update(db)
            }
        }
    }

    fun onDeleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            dictionaryDAO.deleteAll()
        }
    }

}