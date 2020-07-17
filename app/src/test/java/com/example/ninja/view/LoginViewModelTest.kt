package com.example.ninja.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ninja.model.ResponseBodyLogin
import com.example.ninja.model.ResponseLogin
import com.example.ninja.service.LoginService
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class LoginViewModelTest {

    @get: Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var loginService: LoginService

    @InjectMocks
    var viewModel = LoginViewModel()

    private var testSingle: Single<ResponseLogin>? = null

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
    }

    companion object {
        const val tokenDummy = "asDefW243"
        const val emailDummy = "asDefW243"
        const val passwordDummy = "asDefW243"

    }

    @Test
    fun getLoginSuccess() {
        val responseLogin = ResponseLogin(token = tokenDummy)
        val responseBodyLogin = ResponseBodyLogin(email = emailDummy, password = passwordDummy)

        testSingle = Single.just(responseLogin)

        Mockito.`when`(loginService.getResponseLogin(responseBodyLogin)).thenReturn(testSingle)

        viewModel.fetchLogin(responseBodyLogin)
        viewModel.loading.value = false

        Assert.assertEquals(responseLogin, viewModel.response.value)

        Assert.assertEquals(false, viewModel.loading.value)

    }

    @Test
    fun getLoginFail(){
        val responseBodyLogin = ResponseBodyLogin(email = emailDummy, password = passwordDummy)

        testSingle = Single.error(Throwable())

        Mockito.`when`(loginService.getResponseLogin(responseBodyLogin)).thenReturn(testSingle)

        viewModel.fetchLogin(responseBodyLogin)

        Assert.assertEquals("Wrong email and password", viewModel.errorMessage.value)

        Assert.assertEquals(false, viewModel.loading.value)

    }

    @Before
    fun setUpRxSchedulers(){
        val immidiate = object: Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler{ scheduler: Callable<Scheduler>? -> immidiate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler: Callable<Scheduler>? -> immidiate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler: Callable<Scheduler>? ->  immidiate}
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler: Callable<Scheduler>? -> immidiate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler>? -> immidiate }

    }
}