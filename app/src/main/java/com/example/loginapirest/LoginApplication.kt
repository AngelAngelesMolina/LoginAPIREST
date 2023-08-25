package com.example.loginapirest

import android.app.Application

//ayuda al acceso de la bd
class LoginApplication : Application() {
    companion object {
        lateinit var reqResAPI: ReqResAPI
    }

    override fun onCreate() {
        super.onCreate()

        //VOLLEY
        reqResAPI = ReqResAPI.getInstance(this)
    }
}