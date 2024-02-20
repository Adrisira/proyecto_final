package com.asiguenza.proyecto_final.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiguenza.proyecto_final.R
import com.asiguenza.proyecto_final.databinding.FragmentCarritoBinding
import com.asiguenza.proyecto_final.ui.carrito.CarritoElementoAdapter

import com.asiguenza.proyecto_final.ui.carrito.CarritoElementoManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CarritoFragment : Fragment(R.layout.fragment_carrito) {
    private lateinit var binding: FragmentCarritoBinding
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    //IMPORTANTE QUEDA QUE ACTUALICE EL RECYCLERVIEW CADA VEZ QUE HACES ALGO CON LA LISTA
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarritoBinding.bind(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        coroutineScope.launch {
            var listaProductos = CarritoElementoManager.obtenerListaDeProductos()
            //Borrar elementos del carrito al procesarlos
            binding.btnTramitarPedido.setOnClickListener{
                eliminarColeccionCarrito()
                Toast.makeText(requireContext(), "Pedido Tramitado", Toast.LENGTH_SHORT)
                    .show()
                //No funciona PREGUNTAR TAMBIEN
                listaProductos?.clear()
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            //Obtención de los productos

            //Se meten los productos en el carrito fragment
            binding.recyclerView.adapter = CarritoElementoAdapter(listaProductos, this@CarritoFragment){ elemento ->
                //Intento que se reduzca, no funciona
                if(elemento.cantComprada.toInt() - 1 == 0){
                    db.collection("carrito").document(elemento.id).delete()
                    listaProductos?.remove(elemento)
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    db.collection("carrito").document(elemento.id).update("cantComprada", elemento.cantComprada.toInt() -1)
                   //Queda hacer que quitar aqui el -1 del elemento
                    val updatedElement = elemento.copy(cantComprada = (elemento.cantComprada.toInt() - 1))
                    val index = listaProductos?.indexOf(elemento)
                    if (index != -1) {
                        listaProductos!![index!!] = updatedElement
                        binding.recyclerView.adapter?.notifyItemChanged(index)
                    }
                }

                //Si el elemento es 0 pues se tiene que borrar

                binding.recyclerView.adapter?.notifyDataSetChanged()

            }


        }


    }

    //Funcion para eliminar todos los elementos
    private fun eliminarColeccionCarrito(){
        db.collection("carrito").get().addOnSuccessListener { productos ->
            for(producto in productos) {
                producto.reference.delete()
            }
        }
    }


}