package com.example.trivial.modelo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.trivial.bbdd.Repositorio
import kotlinx.coroutines.launch

class PreguntasVM(private val miRepositorio: Repositorio) : ViewModel() {
    lateinit var listaPreguntas: LiveData<List<Pregunta>>
    lateinit var pregunta: LiveData<Pregunta>

    fun mostrarPreguntas() = viewModelScope.launch {
        listaPreguntas = miRepositorio.mostrarPreguntas().asLiveData()
    }

    @WorkerThread
    fun insertarPregunta(miPregunta: Pregunta) = viewModelScope.launch {
        miRepositorio.insertarPregunta(miPregunta)
    }

    @WorkerThread
    fun borrarPregunta(miPregunta: Pregunta) = viewModelScope.launch {
        miRepositorio.borrarPregunta(miPregunta)
    }

    fun buscarPreguntaPorId(id: Int) = viewModelScope.launch {
        pregunta = miRepositorio.buscarPreguntaPorId(id).asLiveData()
    }
}

class PreguntasViewModelFactory(private val miRepositorio: Repositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilesVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PerfilesVM(miRepositorio) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}