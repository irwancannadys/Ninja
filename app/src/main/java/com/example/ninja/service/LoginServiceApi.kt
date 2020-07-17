package com.example.ninja.service

import com.example.ninja.model.ResponseBodyLogin
import com.example.ninja.model.ResponseLogin
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginServiceApi {

    @POST("api/login")
    fun postLogin(@Body user: ResponseBodyLogin): Single<ResponseLogin>
}