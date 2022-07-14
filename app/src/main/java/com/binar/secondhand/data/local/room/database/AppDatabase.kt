package com.binar.secondhand.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binar.secondhand.data.local.room.model.ExampleEntity
import com.binar.secondhand.data.local.room.service.SearchHistoryDao

@Database(entities = [ExampleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao

}