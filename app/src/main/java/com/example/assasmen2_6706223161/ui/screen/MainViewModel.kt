package com.example.assasmen2_6706223161.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assasmen2_6706223161.database.KatalogDao
import com.example.assasmen2_6706223161.model.Katalog
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class MainViewModel(dao: KatalogDao) : ViewModel() {

    val data: StateFlow<List<Katalog>> = dao.getKatalog().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}