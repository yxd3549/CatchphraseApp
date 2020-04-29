package com.example.catchphrase.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.catchphrase.entities.Phrase

@Dao interface PhraseDao {
    @Query("Select phrase from phrase")
    fun getAllPhrases(): List<String>

    @Query("Select phrase from phrase where category = :category")
    fun getPhrasesInCategory(category: String): List<String>

    @Query("Select distinct category from phrase")
    fun getAllCategories(): List<String>

    @Insert(onConflict = REPLACE)
    fun insertPhrase(phrase: Phrase)

    @Delete
    fun deletePhrase(phrase: Phrase)
}