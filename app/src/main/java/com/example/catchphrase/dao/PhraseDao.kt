package com.example.catchphrase.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.catchphrase.entities.Phrase

@Dao interface PhraseDao {
    @Query("Select phrase from phrase")
    fun gerAllPhrases(): List<Phrase>

    @Query("Select phrase from phrase where category_name = :category")
    fun getPhrasesInCategory(category: String): List<Phrase>

    @Insert(onConflict = REPLACE)
    fun insertPhrase(phrase: Phrase)

    @Delete
    fun deletePhrase(phrase: Phrase)
}