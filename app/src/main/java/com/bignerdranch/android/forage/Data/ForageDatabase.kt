package com.bignerdranch.android.forage.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Forage::class], version = 1, exportSchema = false)
abstract class ForageDatabase : RoomDatabase() {
    abstract fun dao(): ForageableDao

    companion object {
        private var INSTANCE: ForageDatabase? = null
        fun getDatabase(context: Context): ForageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ForageDatabase::class.java,
                    "forage_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}