package com.bignerdranch.android.forage.Data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ForageableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forage: Forage)

    @Update
    suspend fun update(forage: Forage)

    @Delete
    suspend fun delete(forage: Forage)

    @Query("SELECT * from forage_database WHERE id = :id ")
    fun getForage(id: Int): Flow<Forage>

    @Query("SELECT * from forage_database ORDER BY name_forage asc")
    fun getForages(): Flow<List<Forage>>
}