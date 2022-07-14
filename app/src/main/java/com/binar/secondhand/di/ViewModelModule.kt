package com.binar.secondhand.di

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.binar.secondhand.ui.auth.login.LoginUserViewModel
import com.binar.secondhand.ui.auth.register.RegisterUserViewModel
import com.binar.secondhand.ui.home.HomeViewModel
import com.binar.secondhand.ui.akun.AkunViewModel
import com.binar.secondhand.ui.akun.edit_akun.UbahAkunViewModel

val viewModelModule = module {

    viewModelOf(::LoginUserViewModel)

    viewModelOf(::RegisterUserViewModel)

    viewModelOf(::HomeViewModel)

    viewModelOf(::AkunViewModel)

    viewModelOf(::UbahAkunViewModel)

}