package com.example.catchphrase

import androidx.room.*
import android.content.Context
import com.example.catchphrase.dao.PhraseDao
import com.example.catchphrase.entities.Phrase


@Database(entities = arrayOf(Phrase::class), version = 3, exportSchema = true)
abstract class PhraseDatabase : RoomDatabase() {

    abstract fun PhraseDao(): PhraseDao

    companion object {
        // The only instance singleton
        private var INSTANCE: PhraseDatabase? = null

        // gets the singleton
        @Synchronized
        fun getInstance(context: Context): PhraseDatabase {
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    PhraseDatabase::class.java,
                    "phrase.db").fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}