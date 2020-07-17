package com.example.ninja.di

import com.example.ninja.service.LoginService
import com.example.ninja.service.LoginServiceApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    val BASE_URL = "https://reqres.in/"

    @Provides
    fun provideNetwork(): LoginServiceApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(LoginServiceApi::class.java)
    }

    @Provides
    fun provideLoginService(): LoginService{
        return LoginService()
    }
}