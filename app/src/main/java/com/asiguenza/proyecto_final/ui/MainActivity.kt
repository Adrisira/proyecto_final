package com.asiguenza.proyecto_final.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asiguenza.proyecto_final.App
import com.asiguenza.proyecto_final.R
import com.asiguenza.proyecto_final.databinding.ActivityLoginActiviyBinding
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginActiviyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val user = (applicationContext as App).auth.getCurrentUser()

        binding.emailUser.text = user?.email
        binding.nameUser.text = user?.displayName
        if (user?.photoUrl != null) {
            Glide.with(binding.imageUser).load(user?.photoUrl).into(binding.imageUser)
        } else {
            Glide.with(binding.imageUser).load("https://picsum.photos/200").into(binding.imageUser)
        }
        binding.button1.setOnClickListener {
            firebaseAnalytics.logEvent("button_clicked"){
                param("button_name", "button1")
            }
        }

        binding.button2.setOnClickListener {
            firebaseAnalytics.logEvent("button_clicked"){
                param("button_name", "button2")
            }
        }

        binding.btnCerrarSesion.setOnClickListener {
            (application as App).auth.signOut()
            finish()
        }
    }
}