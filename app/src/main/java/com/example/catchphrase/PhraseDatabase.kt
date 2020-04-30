package com.example.catchphrase

import androidx.room.*
import android.content.Context
import com.example.catchphrase.dao.PhraseDao
import com.example.catchphrase.entities.Phrase


/**
 * This class represents our database of phrases
 */
@Database(entities = arrayOf(Phrase::class), version = 3, exportSchema = true)
abstract class PhraseDatabase : RoomDatabase() {

    abstract fun phraseDao(): PhraseDao

    companion object {

        private var INSTANCE: PhraseDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PhraseDatabase {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    PhraseDatabase::class.java,
                    "phrase.db")
                    .createFromAsset("base.db")
                    .build()
            }
            return INSTANCE!!
        }
    }
}