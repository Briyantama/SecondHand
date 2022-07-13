package com.binar.secondhand.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binar.secondhand.data.local.room.model.ExampleEntity

@Database(entities = [ExampleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

}