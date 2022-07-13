package com.binar.secondhand.di

import com.binar.secondhand.data.repository.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::Repository)

    singleOf(::LoginRepository)

    singleOf(::RegisterRepository)

    singleOf(::HomeRepository)

    singleOf(::ProductSaleListRepository)
}