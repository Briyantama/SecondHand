package com.binar.secondhand.data.local.room.service

import androidx.room.*
import com.binar.secondhand.data.local.room.model.SearchHistoryEntity

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM searchhistoryentity ORDER BY id DESC")
    fun getSearchHistory(): List<SearchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity): Long

    @Delete
    fun clearHistory(searchHistoryEntity: SearchHistoryEntity): Int

    @Query("DELETE FROM searchhistoryentity")
    fun clearAllHistory(): Int

}