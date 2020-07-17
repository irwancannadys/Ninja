package com.example.ninja.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ninja.R
import com.example.ninja.model.ResponseBodyLogin
import kotlinx.android.synthetic.main.activity_main.*

class MainLoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instantiateLogin()
        instantiateViewModel()
    }

    private fun instantiateLogin() {
        btn_login.setOnClickListener {
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            val responseBodyLogin = ResponseBodyLogin(email = email, password = password)
            viewModel.fetchLogin(responseBodyLogin)
        }
    }

    private fun instantiateViewModel() {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.response.observe(this, Observer {response ->
            val tokenToSave = response.token
            Toast.makeText(this, tokenToSave, Toast.LENGTH_SHORT).show()
            ProfileActivity.toProfileActivity(this)
        })

        viewModel.loading.observe(this, Observer { loading ->
            progress_loading.visibility = if (loading) View.VISIBLE else View.GONE
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }
}