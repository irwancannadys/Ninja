package com.example.ninja.di

import com.example.ninja.service.LoginService
import com.example.ninja.view.LoginViewModel
import dagger.Component


@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: LoginService)
    fun inject(loginViewModel: LoginViewModel)
}