package com.example.news.di.module

import com.example.news.main.owner.fragment.DashboardFragment
import com.example.news.main.owner.fragment.NewsDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun contributeNewsDetailFragment(): NewsDetailFragment

}