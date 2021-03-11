package com.tuit_21019.pdpuzkurslar.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tuit_21019.pdpuzkurslar.models.Guruh
import com.tuit_21019.pdpuzkurslar.models.Kurs
import com.tuit_21019.pdpuzkurslar.models.Mentor
import com.tuit_21019.pdpuzkurslar.models.Talaba

@Database(
    entities = [Guruh::class, Kurs::class, Mentor::class, Talaba::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): DbMethods

    companion object {
        @Volatile
        private var database: AppDatabase? = null

        fun init(context: Context) {
            synchronized(this) {
                if (database == null) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "pdpuz.db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
        }
    }

    object get {
        fun getDatabase(): AppDatabase {
            return database!!
        }
    }
}