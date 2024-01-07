package com.example.wordcount.viewModel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.wordcount.R
import com.example.wordcount.dataBase.App
import com.example.wordcount.databinding.FragmentDictionaryBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DictionaryFragment : Fragment() {
    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DictionaryViewModel by viewModels {

        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataBase = (activity?.application as App).db.getDictionaryDAO()
                return DictionaryViewModel(dataBase) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDictionaryBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener {
            val word = binding.editTextEnter.text.toString()
            if (!validateInput(word)) {
                Snackbar.make(binding.btnAdd, getString(R.string.invalid_value), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                viewModel.dictionaryAll.collect { list ->
                    var counter = true
                    list.forEach {
                        if (word == it.word) {
                            viewModel.onUpdateWordCount(it)
                            counter = false
                        }
                    }
                    if (counter) viewModel.onCreateDictionary(word)
                }
            }.cancel()
        }

        binding.btnClear.setOnClickListener {
            viewModel.onDeleteAllData()
        }

        lifecycleScope.launch {
            viewModel.dictionaryAll.collect {
                var ls = it.sortedByDescending { it.wordCount }
                if (it.size > 5) ls = it.sortedByDescending { it.wordCount }.take(5)
                binding.tvResult.text = ls.joinToString(
                    "\r\n"
                )
            }
        }

    }

    private fun validateInput(input: String): Boolean {
        // Проверяем, содержит ли строка только буквы и дефисы, и не содержит пробелов, цифр, точек или запятых
        val regex = "^[A-Za-z-]+$".toRegex()
        return regex.matches(input)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}