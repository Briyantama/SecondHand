package com.binar.secondhand.data.local.room.service

import androidx.room.*
import com.binar.secondhand.data.local.room.model.SearchHistoryEntity

@Dao
interface SearchHistoryDao {

//    @Query("SELECT * FROM searchhistoryentity ORDER BY id DESC")
//    suspend fun getSearchHistory(): List<SearchHistoryEntity>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity): Long
//
//    @Delete
//    suspend fun clearHistory(searchHistoryEntity: SearchHistoryEntity): Int
//
//    @Query("DELETE FROM searchhistoryentity")
//    suspend fun clearAllHistory(): Int

}