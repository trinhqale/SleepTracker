package com.example.sleeptracker

import android.widget.RemoteViews
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDAO {
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(item: Item)
//    @Update
//    suspend fun update(item: Item)
//    @Delete
//    suspend fun delete(item:Item)
//    @Query("SELECT * from item_table WHERE id=:id")
//    fun getItem(id:Int): Flow<Item>
//    @Query("SELECT * FROM item_table ORDER BY day ASC")
//    fun getItems(): Flow<List<Item>>
    @Query("SELECT * FROM item_table ORDER BY day ASC")
    fun getAll(): Flow<List<Item>>

    @Insert
    fun insertAll(items: List<Item>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Item)

    @Query("DELETE FROM item_table")
    fun deleteAll()

    @Query("DELETE FROM item_table WHERE id=:id ")
    fun deleteEntity(id: Long)
}