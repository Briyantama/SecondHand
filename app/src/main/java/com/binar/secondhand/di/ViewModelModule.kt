package com.binar.secondhand.di

import com.binar.secondhand.ui.bagian_akun.akun.AkunViewModel
import com.binar.secondhand.ui.bagian_akun.change_pass.ChangePassViewModel
import com.binar.secondhand.ui.bagian_akun.edit_akun.UbahAkunViewModel
import com.binar.secondhand.ui.bagian_auth.login.LoginUserViewModel
import com.binar.secondhand.ui.bagian_auth.register.RegisterUserViewModel
import com.binar.secondhand.ui.bagian_home.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::LoginUserViewModel)

    viewModelOf(::RegisterUserViewModel)

    viewModelOf(::HomeViewModel)

    viewModelOf(::AkunViewModel)

    viewModelOf(::UbahAkunViewModel)

    viewModelOf(::ChangePassViewModel)
}