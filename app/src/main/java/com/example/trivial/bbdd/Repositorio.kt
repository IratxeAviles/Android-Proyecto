package com.example.trivial.bbdd

import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trivial.modelo.Pregunta
import com.example.trivial.modelo.Puntuacion
import kotlinx.coroutines.flow.Flow

class Repositorio(val miDAO: DAO) {
    fun mostrarPuntuaciones(): Flow<List<Puntuacion>> {
        return miDAO.mostrarPuntuaciones()
    }

    @WorkerThread
    suspend fun insertarPuntuacion(miPuntuacion: Puntuacion) {
        miDAO.insertarPuntuacion(miPuntuacion)
    }

    @WorkerThread
    suspend fun modificarPregunta(miPregunta: Pregunta) {
        miDAO.modificarPregunta(miPregunta)
    }

    @WorkerThread
    suspend fun borrarPuntuacion(miPuntuacion: Puntuacion) {
        miDAO.borrarPuntuacion(miPuntuacion)
    }

    fun buscarPuntuacionPorNombre(nombre: String): Flow<Puntuacion> {
        return miDAO.buscarPuntuacionPorNombre(nombre)
    }

    fun mostrarPreguntas(): Flow<List<Pregunta>> {
        return miDAO.mostrarPreguntas()
    }

    @WorkerThread
    suspend fun insertarPregunta(miPregunta: Pregunta) {
        miDAO.insertarPregunta(miPregunta)
    }

    @WorkerThread
    suspend fun modificarPuntuacion(miPuntuacion: Puntuacion) {
        miDAO.modificarPuntuacion(miPuntuacion)
    }

    @WorkerThread
    suspend fun borrarPregunta(miPregunta: Pregunta) {
        miDAO.borrarPregunta(miPregunta)
    }

    fun buscarPreguntaPorId(id: Int): Flow<Pregunta> {
        return miDAO.buscarPreguntaPorId(id)
    }
}