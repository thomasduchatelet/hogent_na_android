package com.example.tequilapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bar_table")
data class Bar(
    @PrimaryKey(autoGenerate = true)
    var barId: Long = 0L,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "price")
    var price: Double = 0.0,

    @ColumnInfo(name = "quality_rating")
    var tequilaQuality: Double = 0.0,

    @ColumnInfo(name = "number_of_ratings")
    var numberOfRatings: Int = 0
)