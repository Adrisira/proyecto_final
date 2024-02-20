package com.asiguenza.proyecto_final.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.asiguenza.proyecto_final.core.AuthManager
import com.asiguenza.proyecto_final.core.AuthRes
import com.asiguenza.proyecto_final.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val auth = AuthManager(this)//(application as App).auth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val googleSignLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            when (val account =
                auth.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))) {
                is AuthRes.Success -> {
                    val credential = GoogleAuthProvider.getCredential(account.data?.idToken, null)
                    GlobalScope.launch {
                        val firebaseUser = auth.googleSignInCredential(credential)
                        when (firebaseUser) {
                            is AuthRes.Success -> {
                                Snackbar.make(
                                    binding.root,
                                    "Inicio de sesión correcto",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            }

                            is AuthRes.Error -> {
                                Snackbar.make(
                                    binding.root,
                                    "Error al iniciar sesión",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                is AuthRes.Error -> {
                    Snackbar.make(binding.root, "Error al iniciar sesión", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }


        if (auth.getCurrentUser() != null){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        with(binding){
            tvRegistro.setOnClickListener {
                val intent = Intent(this@LoginActivity, CrearCuenta::class.java)
                startActivity(intent)
            }

            btnInicioSesion.setOnClickListener {
                emailPassSignIn(etEmail.text.toString(), etPassword.text.toString())
            }

            tvOlvidasteContrasena.setOnClickListener {
                val intent = Intent(this@LoginActivity, RecuperaContrasena::class.java)
                startActivity(intent)
            }

            inicioGoogle.setOnClickListener {
                auth.signInWithGoogle(googleSignLauncher)
            }
        }

    }

    private fun emailPassSignIn(eMail: String, password: String) {
        if (!eMail.isNullOrEmpty() && !password.isNullOrEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                when (auth.signInWithEmailAndPassword(
                    eMail,
                    password
                )){
                    is AuthRes.Success -> {
                        Snackbar.make(binding.root, "Inicio de sesión correcto", Snackbar.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    is AuthRes.Error -> {
                        Snackbar.make(binding.root, "Error al iniciar sesión", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}