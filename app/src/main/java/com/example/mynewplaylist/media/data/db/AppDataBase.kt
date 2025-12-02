package com.example.mynewplaylist.media.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mynewplaylist.media.data.db.dao.TrackDao
import com.example.mynewplaylist.media.data.db.entity.TrackEntity

@Database(version = 1, entities = [TrackEntity::class])
abstract class AppDataBase: RoomDatabase() {
    abstract fun trackDao(): TrackDao
    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        // Миграция с версии 1 на 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Добавляем поле isFavorite его не было
                database.execSQL("ALTER TABLE track_table ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getInstance(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_1_2) // Добавь миграцию
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}