package com.binar.secondhand.di

import com.binar.secondhand.data.repository.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {

    singleOf(::LoginRepository)

    singleOf(::RegisterRepository)

    singleOf(::HomeRepository)

    singleOf(::SearchHistoryRepository)

    singleOf(::Repository)

    singleOf(::ProductSaleListRepository)
}