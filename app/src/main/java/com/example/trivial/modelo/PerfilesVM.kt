package com.example.trivial.modelo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.trivial.bbdd.Repositorio
import kotlinx.coroutines.launch

class PerfilesVM(private val miRepositorio: Repositorio) : ViewModel() {
    // val listaPerfiles = MutableLiveData<List<Pelicula>>() // primera manera: por si hay posibilidad de no recibir datos
    lateinit var listaPerfiles: LiveData<List<Perfil>>
    lateinit var perfil: LiveData<Perfil> // segunda manera: se puede usar si sabes que va a haber dato si o si

    fun mostrarPerfiles() = viewModelScope.launch {
        listaPerfiles = miRepositorio.mostrarPerfiles().asLiveData()
    }

    @WorkerThread
    fun insertarPerfil(miPerfil: Perfil) = viewModelScope.launch {
        miRepositorio.insertarPerfil(miPerfil)
    }

    @WorkerThread
    fun borrarPerfil(miPerfil: Perfil) = viewModelScope.launch {
        miRepositorio.borrarPerfil(miPerfil)
    }

    @WorkerThread
    fun modificarPerfil(miPerfil: Perfil) = viewModelScope.launch {
        miRepositorio.modificarPerfil(miPerfil)
    }

    fun buscarPerfilPorId(id: Int) = viewModelScope.launch {
        perfil = miRepositorio.buscarPerfilPorId(id).asLiveData()
    }
}

class PerfilesViewModelFactory(private val miRepositorio: Repositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilesVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PerfilesVM(miRepositorio) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}

/*
class PerfilVM: ViewModel() {
    var usuario: Perfil?=null
    var preguntas: MutableList<Pregunta> = mutableListOf()
    var aciertos: Pregunta?=null // no se si asi o siendo int
}

 */