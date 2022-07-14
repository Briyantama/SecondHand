package com.binar.secondhand.app

import android.app.Application
import android.content.Context
import com.binar.secondhand.helper.Sharedpref
import com.binar.secondhand.di.databaseModule
import com.binar.secondhand.di.networkModule
import com.binar.secondhand.di.repositoryModule
import com.binar.secondhand.di.viewModelModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SecondHandApp: Application() {

    init {
        instance = this
    }
    companion object {
        private lateinit var instance: SecondHandApp
        private lateinit var sharedpref: Sharedpref
        fun getContext(): Context {
            return instance.applicationContext
        }
    }
    override fun onCreate() {
        super.onCreate()
        sharedpref = Sharedpref(getContext())
        val token = sharedpref.getStringKey("token")
        FirebaseApp.initializeApp(this)
        startKoin {
            androidLogger()
            androidContext(getContext())
            modules(
                repositoryModule,
                viewModelModule,
                networkModule,
                databaseModule
            )
            if (!token.isNullOrEmpty()){
                koin.setProperty("access_token", token)
            }
        }
    }
}