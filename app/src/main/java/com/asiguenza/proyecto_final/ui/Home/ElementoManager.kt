package com.asiguenza.proyecto_final.ui.Home

import android.util.Log
import com.asiguenza.proyecto_final.model.Producto
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class ElementoManager {


    companion object {
        suspend fun obtenerListaDeProductos(): MutableList<Producto>? {
            return withContext(Dispatchers.Default) {
                delay(200)
                val productosTotal: MutableList<Producto>? = mutableListOf()
                val db = FirebaseFirestore.getInstance()
                val productosCollection = db.collection("producto")
                try {
                    val productos = productosCollection.get().await()
                    for (producto in productos) {
                        val id = producto.id
                        val nombre = producto.getString("nombre") ?: ""
                        val descripcion = producto.getString("descripcion") ?: ""
                        val precio = producto.getString("precio") ?: ""
                        val imagen = producto.getString("imagen") ?: ""
                        val productoProc = Producto(id, nombre, descripcion, precio, imagen)
                        productosTotal?.add(productoProc)
                    }
                } catch (e: Exception) {
                    Log.e("ElementoManager", "Error: ${e.message}")
                }
                productosTotal
            }
        }

    }



}