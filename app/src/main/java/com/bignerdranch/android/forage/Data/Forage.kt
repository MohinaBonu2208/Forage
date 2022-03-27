package com.bignerdranch.android.forage.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forage_database")
data class Forage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name_forage") val name: String,
    @ColumnInfo(name = "location_forage") val location: String,
    @ColumnInfo(name = "in_season") val inSeason: Boolean,
    @ColumnInfo(name = "note")val notes: String?
)