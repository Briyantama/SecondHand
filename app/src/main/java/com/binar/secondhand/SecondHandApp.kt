package com.binar.secondhand

import android.app.Application
import android.content.Context
import com.binar.secondhand.di.databaseModule
import com.binar.secondhand.di.networkModule
import com.binar.secondhand.di.repositoryModule
import com.binar.secondhand.di.viewModelModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SecondHandApp: Application() {

    init {
        instance = this
    }
    companion object {
        private lateinit var instance: SecondHandApp

        fun getContext(): Context {
            return instance.applicationContext
        }
    }
    override fun onCreate() {
        super.onCreate()
        val pref = instance.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val token = pref.getString("token", "")
        FirebaseApp.initializeApp(this)
        startKoin {
            androidLogger()
            androidContext(this@SecondHandApp)
            if (!token.isNullOrEmpty()){
                koin.setProperty("access_token", token)
            }
            modules(
                repositoryModule,
                viewModelModule,
                networkModule,
                databaseModule
            )
        }
    }
}