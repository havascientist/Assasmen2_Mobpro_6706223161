package com.example.assasmen2_6706223161.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.assasmen2_6706223161.model.Katalog
import kotlinx.coroutines.flow.Flow

@Dao
interface KatalogDao {

    @Insert
    suspend fun insert(katalog: Katalog)

    @Update
    suspend fun update(katalog: Katalog)

    @Query("SELECT * FROM katalog ORDER BY jenis_barang ASC")
    fun getKatalog(): Flow<List<Katalog>>

    @Query("SELECT * FROM katalog WHERE id = :id")
    suspend fun getKatalogById(id: Long): Katalog?
    @Query("DELETE FROM katalog WHERE id = :id")
    suspend fun deleteById(id: Long)
}