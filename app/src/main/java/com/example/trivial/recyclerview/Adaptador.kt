package com.example.trivial.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.trivial.R
import com.example.trivial.databinding.RecyclerviewItemBinding
import com.example.trivial.modelo.Puntuacion

class Adaptador(val lista: MutableList<Puntuacion>) : RecyclerView.Adapter<Adaptador.ViewHolder>() {
    inner class ViewHolder(val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var id: Int = -1

        init {
            binding.llRecyclerItem.setOnClickListener {
                val bundle = bundleOf("id" to id)
                binding.llRecyclerItem.findNavController()
                    .navigate(R.id.action_puntuacionesFragment_to_firstFragment, bundle)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvUsuario.text = lista[position].usuario
        holder.binding.tvPuntuacion.text = lista[position].record.toString()
        holder.id = lista[position].id
    }

    override fun getItemCount(): Int {
        return lista.count()
    }
}