package com.asiguenza.proyecto_final.ui.Home

import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiguenza.proyecto_final.R
import com.asiguenza.proyecto_final.databinding.ViewElementoBinding
import com.asiguenza.proyecto_final.model.Producto
import com.asiguenza.proyecto_final.ui.HomeFragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class ElementoAdapter(
    val elementos: MutableList<Producto>?,
    val context: HomeFragment,
    val listener: (Producto) -> Unit
) : RecyclerView.Adapter<ElementoAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ViewElementoBinding.bind(view)

        fun bind(elemento: Producto) {
            //Aqui es donde vamos a añadir todo despues
            binding.tvNombre.text = elemento.nombre
            binding.tvPrecio.text = elemento.precio
            binding.tvDescripcion.text = elemento.descripcion
            Glide.with(binding.root).load(elemento.imagen).into(binding.ivImagen)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_elemento, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount() = elementos!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(elementos!![position])
        val elemento = elementos[position]
        holder.bind(elemento)
        holder.binding.btnComprar.setOnClickListener {
            listener(elemento)

        }
    }

}