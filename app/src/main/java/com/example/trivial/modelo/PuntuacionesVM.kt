package com.example.trivial.modelo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.trivial.bbdd.Repositorio
import kotlinx.coroutines.launch

class PuntuacionesVM(private val miRepositorio: Repositorio) : ViewModel() {
    // val listaPuntuaciones = MutableLiveData<List<Pelicula>>() // primera manera: por si hay posibilidad de no recibir datos
    lateinit var listaPuntuaciones: LiveData<List<Puntuacion>>
    lateinit var puntuacion: LiveData<Puntuacion> // segunda manera: se puede usar si sabes que va a haber dato si o si

    fun mostrarPuntuaciones() = viewModelScope.launch {
        listaPuntuaciones = miRepositorio.mostrarPuntuaciones().asLiveData()
    }

    @WorkerThread
    fun insertarPuntuacion(miPuntuacion: Puntuacion) = viewModelScope.launch {
        miRepositorio.insertarPuntuacion(miPuntuacion)
    }

    @WorkerThread
    fun borrarPuntuacion(miPuntuacion: Puntuacion) = viewModelScope.launch {
        miRepositorio.borrarPuntuacion(miPuntuacion)
    }

    fun buscarPuntuacionPorId(id: Int) = viewModelScope.launch {
        puntuacion = miRepositorio.buscarPuntuacionPorId(id).asLiveData()
    }
}

class PuntuacionesViewModelFactory(private val miRepositorio: Repositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PuntuacionesVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PuntuacionesVM(miRepositorio) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}