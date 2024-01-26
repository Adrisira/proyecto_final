package com.asiguenza.proyecto_final.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asiguenza.proyecto_final.App
import com.asiguenza.proyecto_final.core.AuthRes
import com.asiguenza.proyecto_final.databinding.ActivityRecuperarContrasenaBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecuperaContrasena : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecuperarContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRecuperaContrasena.setOnClickListener {
            GlobalScope.launch {
                when ((application as App).auth.resetPassword(binding.etEmail.text.toString())){
                    is AuthRes.Success -> {
                        Snackbar.make(binding.root, "Correo enviado correctamente", Snackbar.LENGTH_SHORT).show()
                        finish()
                    }
                    is AuthRes.Error -> {
                        Snackbar.make(binding.root, "Error al enviar el correo", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}