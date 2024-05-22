package com.example.ejercicio6.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trivial.databinding.RecyclerviewItemBinding
import com.example.trivial.modelo.Puntuacion

class Adaptador(val lista: MutableList<Puntuacion>) : RecyclerView.Adapter<Adaptador.ViewHolder>() {
    inner class ViewHolder (val binding: RecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
        var id:Int=-1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tUsuario.text=lista[position].usuario
        holder.binding.tPuntuacion.text=lista[position].record.toString()
        holder.id=lista[position].id
    }

    override fun getItemCount(): Int {
        return lista.count()
    }
}