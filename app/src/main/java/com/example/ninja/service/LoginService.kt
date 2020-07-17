package com.example.ninja.service

import com.example.ninja.di.DaggerApiComponent
import com.example.ninja.model.ResponseBodyLogin
import com.example.ninja.model.ResponseLogin
import io.reactivex.Single
import javax.inject.Inject

class LoginService {

    @Inject
    lateinit var loginServiceApi: LoginServiceApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getResponseLogin(responseBodyLogin: ResponseBodyLogin): Single<ResponseLogin>{
        return loginServiceApi.postLogin(responseBodyLogin)
    }
}
