package com.example.news.di

import android.app.Application
import com.example.news.di.module.ActivityModule
import com.example.news.di.module.AppModule
import com.example.news.util.application.NewsApplication


import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (AndroidInjectionModule::class), (AppModule::class), (ActivityModule::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: NewsApplication)
}