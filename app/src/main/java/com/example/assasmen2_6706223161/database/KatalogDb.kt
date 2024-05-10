package com.example.assasmen2_6706223161.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assasmen2_6706223161.model.Katalog

@Database(entities = [Katalog::class], version = 1, exportSchema = false)
abstract class KatalogDb : RoomDatabase() {
    abstract val dao:KatalogDao
    companion object {
        @Volatile
        private var INSTANCE:KatalogDb? = null
        fun getInstance(context: Context): KatalogDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KatalogDb::class.java,
                        "katalog.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}