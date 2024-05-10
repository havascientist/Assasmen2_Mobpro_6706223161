package com.example.assasmen2_6706223161.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "katalog")
data class Katalog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama_barang: String,
    val kode_barang: String,
    val jenis_barang: String,
    var selectedOption: String = "",
    val deskripsi: String
)