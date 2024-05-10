package com.example.assasmen2_6706223161.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assasmen2_6706223161.database.KatalogDao
import com.example.assasmen2_6706223161.model.Katalog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: KatalogDao) : ViewModel() {
    fun insert(nama_barang: String, kode_barang: String, jenis_barang: String, deskripsi: String) {
        val katalog = Katalog(
            nama_barang = nama_barang,
            kode_barang = kode_barang,
            jenis_barang = jenis_barang,
            deskripsi = deskripsi,
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(katalog)
        }
    }
    suspend fun getKatalog(id: Long): Katalog? {
        return dao.getKatalogById(id)
    }
    fun update(id: Long, nama_barang: String, kode_barang: String, jenis_barang: String, deskripsi: String ) {
        val katalog = Katalog(
            id = id,
            nama_barang = nama_barang,
            kode_barang = kode_barang,
            jenis_barang = jenis_barang,
            deskripsi = deskripsi,
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(katalog)
        }
    }
    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}