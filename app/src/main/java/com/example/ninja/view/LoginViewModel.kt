package com.example.ninja.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ninja.di.DaggerApiComponent
import com.example.ninja.model.ResponseBodyLogin
import com.example.ninja.model.ResponseLogin
import com.example.ninja.service.LoginService
import com.example.ninja.service.LoginServiceApi
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel : ViewModel() {

    @Inject
    lateinit var loginService: LoginService
    private val disposable = CompositeDisposable()

    val response = MutableLiveData<ResponseLogin>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun fetchLogin(responseBodyLogin: ResponseBodyLogin) {
        loading.value = true
        disposable.add(
            loginService.getResponseLogin(responseBodyLogin)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseLogin>() {
                    override fun onSuccess(value: ResponseLogin?) {
                        response.value = value
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loading.value = false
                        errorMessage.value = "Wrong email and password"
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}