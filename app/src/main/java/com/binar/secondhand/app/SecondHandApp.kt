package com.binar.secondhand.app

import android.app.Application
import com.binar.secondhand.di.databaseModule
import com.binar.secondhand.di.networkModule
import com.binar.secondhand.di.repositoryModule
import com.binar.secondhand.di.viewModelModule
import com.binar.secondhand.helper.Sharedpref
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SecondHandApp: Application() {

    private val sharedpref get() = Sharedpref(this)

    override fun onCreate() {
        super.onCreate()
        val token = sharedpref.getStringKey("token")
        FirebaseApp.initializeApp(this)
        startKoin {
            androidLogger()
            androidContext(this@SecondHandApp)
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            )
            if (!token.isNullOrEmpty()){
                koin.setProperty("access_token", token)
            }
        }
    }
}