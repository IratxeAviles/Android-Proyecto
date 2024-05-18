package com.example.ejercicio6.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.trivial.R
import com.example.trivial.databinding.RecyclerviewItemBinding
import com.example.trivial.modelo.Puntuacion
import com.example.trivial.modelo.VM

class Adaptador(val puntuaciones: MutableList<Puntuacion>) : RecyclerView.Adapter<Adaptador.ViewHolder>() {
    inner class ViewHolder (val binding: RecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
        var id:Int=-1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tUsuario.text=puntuaciones[position].usuario //"Título: ${peliculas[position].titulo}"
        holder.binding.tPuntuacion.text=puntuaciones[position].puntuacion.toString() //"Género: ${peliculas[position].genero}"
        holder.id=puntuaciones[position].id
    }

    override fun getItemCount(): Int {
        return puntuaciones.count()
    }
}