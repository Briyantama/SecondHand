package com.binar.secondhand.di

import com.binar.secondhand.ui.bagian_akun.akun.AkunViewModel
import com.binar.secondhand.ui.bagian_akun.change_pass.ChangePassViewModel
import com.binar.secondhand.ui.bagian_akun.edit_akun.UbahAkunViewModel
import com.binar.secondhand.ui.bagian_auth.login.LoginUserViewModel
import com.binar.secondhand.ui.bagian_auth.register.RegisterUserViewModel
import com.binar.secondhand.ui.bagian_home.home.HomeViewModel
import com.binar.secondhand.ui.bagian_home.search.SearchViewModel
import com.binar.secondhand.ui.bagian_product.add_product.AddProductViewModel
import com.binar.secondhand.ui.bagian_product.detail.DetailViewModel
import com.binar.secondhand.ui.bagian_product.preview.PreviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::LoginUserViewModel)

    viewModelOf(::RegisterUserViewModel)

    viewModelOf(::HomeViewModel)

    viewModelOf(::AkunViewModel)

    viewModelOf(::UbahAkunViewModel)

    viewModelOf(::ChangePassViewModel)

    viewModelOf(::PreviewViewModel)

    viewModelOf(::AddProductViewModel)

    viewModelOf(::SearchViewModel)

    viewModelOf(::DetailViewModel)

}