package ru.chieffly.memoscope.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import ru.chieffly.memoscope.model.MemDto


@Database(entities = [MemDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memDao(): MemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mem_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}