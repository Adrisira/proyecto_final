package com.asiguenza.proyecto_final.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.asiguenza.proyecto_final.App
import com.asiguenza.proyecto_final.R
import com.asiguenza.proyecto_final.core.AuthManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore.*



class UserFragment() : Fragment(R.layout.fragment_user) {

    private lateinit var contexto: Context
    private lateinit var  auth  : AuthManager
    private val db = getInstance()
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto = context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = AuthManager(contexto)
        val btnCerrarSesion = view.findViewById<Button>(R.id.btnCerrarSesion)
        val userName = view.findViewById<EditText>(R.id.edtUsuario)
        val userDireccion = view.findViewById<EditText>(R.id.edtDireccion)
        val userTelefono = view.findViewById<EditText>(R.id.edtTelefono)
        val user = auth.getCurrentUser()
        val data = user?.email
        val btnGuardarUsuario = view.findViewById<Button>(R.id.btnGuardarUsuario)


        btnCerrarSesion.setOnClickListener {
            (requireActivity().application as App).auth.signOut()
            db.collection("carrito").get().addOnSuccessListener { productos ->
                for(producto in productos) {
                    producto.reference.delete()
                }
            }
            requireActivity().finish()
        }


        btnGuardarUsuario.setOnClickListener(){
            if (data != null) {
                db.collection("users").document(data).set(
                    hashMapOf(
                        "nombre" to userName.text.toString(),
                        "direccion" to userDireccion.text.toString(),
                        "telefono" to userTelefono.text.toString()
                    )
                )
            }
            userName.text.clear()
            userDireccion.text.clear()
            userTelefono.text.clear()
        }

        val btnEliminarUsuario = view.findViewById<Button>(R.id.btnEliminarUsuario)
        btnEliminarUsuario.setOnClickListener(){
            if (data != null) {
                db.collection("users").document(data).delete()
            }
            userName.text.clear()
            userDireccion.text.clear()
            userTelefono.text.clear()
        }


    }

}