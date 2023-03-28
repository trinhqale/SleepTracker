package com.example.sleeptracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDAO
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
//        fun getDatabase(context: Context): AppDatabase{
//            return INSTANCE?: synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "item_database"
//                ).fallbackToDestructiveMigration().build()
//                INSTANCE = instance
//                return instance
//            }
//        }

    fun getInstance(context: Context): AppDatabase =
        INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

    private fun buildDatabase(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "item-db"
        ).build()

    }


}