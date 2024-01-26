package com.asiguenza.proyecto_final

import android.app.Application
import com.asiguenza.proyecto_final.core.AuthManager

class App() : Application() {
    lateinit var auth: AuthManager

    override fun onCreate() {
        super.onCreate()
        auth = AuthManager(this)
    }
}