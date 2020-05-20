package com.example.news.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.di.ViewModelKey
import com.example.news.factory.AppModelFactory
import com.example.news.main.observer.DashboardViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: DashboardViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppModelFactory): ViewModelProvider.Factory
}