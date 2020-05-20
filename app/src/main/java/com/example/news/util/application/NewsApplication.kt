package com.example.news.util.application

import android.app.Activity
import android.app.Application
import androidx.multidex.MultiDex
import com.example.news.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class NewsApplication: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        AppInjector().init(this)
        instance = this
        MultiDex.install(applicationContext)
    }

    companion object {
        @get:Synchronized
        lateinit var instance: NewsApplication
    }

}