package com.example.news.di.module

import android.annotation.SuppressLint
import android.app.Application
import androidx.room.Room
import com.example.news.database.LocalDatabase
import com.example.news.database.MainDao
import com.example.news.remote.WebService
import com.example.news.remote.WebServiceHolder
import com.example.news.util.helperUtils.AppConfig
import com.example.news.util.helperUtils.AppExecutors
import com.example.news.util.remoteUtils.LiveDataCallAdapterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [(ViewModelModule::class)])
class AppModule {

    @Provides
    fun provideExecutor(): AppExecutors {
        return AppExecutors()
    }


    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create();
    }

    private var BASE_URL: String = AppConfig.BASE_URL

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (AppConfig.DEBUG_MODE) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }
        return builder.build()
    }

    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMainDao(localDatabase: LocalDatabase): MainDao {
        return localDatabase.mainDao()
    }


    @Provides
    @Singleton
    fun provideDatabase(application: Application): LocalDatabase {
        val database = Room.databaseBuilder(
            application,
            LocalDatabase::class.java,
            "news.db"
        )
            .fallbackToDestructiveMigration()
            .build()

        return database

    }

    @Provides
    fun webServiceHolder(): WebServiceHolder {
        return WebServiceHolder.instance
    }


    @Provides
    @Singleton
    fun provideApiWebservice(restAdapter: Retrofit): WebService {
        val webService = restAdapter.create(WebService::class.java)
        WebServiceHolder.instance.setAPIService(webService)
        return webService
    }

}