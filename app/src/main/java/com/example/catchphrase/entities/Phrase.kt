package com.example.catchphrase.entities

import androidx.room.*

@Entity(tableName = "phrase")
data class Phrase(
    @ColumnInfo(name = "phrase") var phrase: String,
    @ColumnInfo(name = "category") var category: String
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}