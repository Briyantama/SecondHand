package com.binar.secondhand.di

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.binar.secondhand.ui.login.LoginUserViewModel
import com.binar.secondhand.ui.login.RegisterUserViewModel
import com.binar.secondhand.ui.home.HomeViewModel

val viewModelModule = module {
    viewModelOf(::LoginUserViewModel)

    viewModelOf(::RegisterUserViewModel)

    viewModelOf(::HomeViewModel)
}