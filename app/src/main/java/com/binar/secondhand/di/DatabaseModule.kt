package com.binar.secondhand.di

import androidx.room.Room
import com.binar.secondhand.data.local.room.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(get()
            , AppDatabase::class.java, "mydatabase.db").build()
    }

}