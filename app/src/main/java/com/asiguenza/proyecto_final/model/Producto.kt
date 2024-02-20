package com.asiguenza.proyecto_final.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Producto(
    var id: String = "",
    val nombre: String,
    val descripcion: String,
    val precio: String,
    val imagen: String,
    var cantComprada: Number = 0
) : Parcelable {
    //Esto nos sirve para crear el objeto en la bd del tiron
    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "nombre" to nombre,
            "descripcion" to descripcion,
            "precio" to precio,
            "imagen" to imagen,
            "cantComprada" to cantComprada
        )
    }
}
