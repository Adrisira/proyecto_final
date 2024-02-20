package com.asiguenza.proyecto_final.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiguenza.proyecto_final.R
import com.asiguenza.proyecto_final.databinding.FragmentHomeBinding
import com.asiguenza.proyecto_final.ui.Home.ElementoAdapter
import com.asiguenza.proyecto_final.ui.Home.ElementoManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        coroutineScope.launch {
            val listaProductos = ElementoManager.obtenerListaDeProductos()
            binding.recyclerView.adapter =
                ElementoAdapter(listaProductos, this@HomeFragment) { elemento ->
                    db.collection("carrito").document(elemento.id).get().addOnSuccessListener {
                        if(it.exists()){
                            val cantComprada = it.get("cantComprada").toString().toInt()
                            db.collection("carrito").document(elemento.id).update("cantComprada",cantComprada + 1 )
                        }else{
                            db.collection("carrito").document(elemento.id)
                                .set(elemento.toMap()).addOnSuccessListener {
                                    Toast.makeText(requireContext(), "Producto añadido", Toast.LENGTH_SHORT)
                                        .show()
                                    db.collection("carrito").document(elemento.id).update("cantComprada", 1)
                                }
                        }
                    }



                }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        coroutineScope.cancel()
    }


}


