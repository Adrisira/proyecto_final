package com.asiguenza.proyecto_final.ui.carrito

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiguenza.proyecto_final.R
import com.asiguenza.proyecto_final.databinding.ViewElementoBinding
import com.asiguenza.proyecto_final.databinding.ViewElementoCarritoBinding
import com.asiguenza.proyecto_final.model.Producto
import com.asiguenza.proyecto_final.ui.CarritoFragment
import com.asiguenza.proyecto_final.ui.Home.ElementoAdapter
import com.bumptech.glide.Glide

class CarritoElementoAdapter(
    val elementos: MutableList<Producto>?,
    val context: CarritoFragment,
    val listener: (Producto) -> Unit
) : RecyclerView.Adapter<CarritoElementoAdapter.ViewHolder>() {
    //Esto nos servira para actualizar la lista del recyclerview

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ViewElementoCarritoBinding.bind(view)

        fun bind(elemento: Producto) {
            //Aqui es donde vamos a añadir todo despues
            binding.tvNombreCarrito.text = elemento.nombre
            binding.tvPrecioCarrito.text = elemento.precio
            binding.tvDescripcionCarrito.text = elemento.descripcion
            binding.tvCantidad.text = elemento.cantComprada.toString()
            Glide.with(binding.root).load(elemento.imagen).into(binding.ivImagenCarrito)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarritoElementoAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_elemento_carrito, parent, false)
        return CarritoElementoAdapter.ViewHolder(view)
    }

    override fun getItemCount() = elementos!!.size

    override fun onBindViewHolder(holder: CarritoElementoAdapter.ViewHolder, position: Int) {
        holder.bind(elementos!![position])
        val elemento = elementos[position]
        holder.bind(elemento)
        holder.binding.btnBorrar.setOnClickListener {
            listener(elemento)

        }
    }

}
