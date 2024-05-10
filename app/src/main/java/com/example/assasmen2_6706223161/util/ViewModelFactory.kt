package com.example.assasmen2_6706223161.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assasmen2_6706223161.database.KatalogDao
import com.example.assasmen2_6706223161.ui.screen.DetailViewModel
import com.example.assasmen2_6706223161.ui.screen.MainViewModel

class ViewModelFactory(private val dao: KatalogDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}