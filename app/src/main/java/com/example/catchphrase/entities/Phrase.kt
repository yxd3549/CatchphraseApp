package com.example.catchphrase.entities

import androidx.room.*

/**
 * The Phrase Entity Dataclass to store our new categories and phrases using Room
 */
@Entity(tableName = "phrase")
data class Phrase(
    @ColumnInfo(name = "phrase") var phrase: String,
    @ColumnInfo(name = "category") var category: String
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}