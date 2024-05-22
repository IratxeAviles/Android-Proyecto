package com.example.trivial.bbdd

import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trivial.modelo.Perfil
import com.example.trivial.modelo.Pregunta
import kotlinx.coroutines.flow.Flow

class Repositorio (val miDAO: DAO){
    fun mostrarPerfiles(): Flow<List<Perfil>> {
        return miDAO.mostrarPerfiles()
    }
    @WorkerThread
    suspend fun insertarPerfil(miPerfil: Perfil){
        miDAO.insertarPerfil(miPerfil)
    }
    @WorkerThread
    suspend fun borrarPerfil(miPerfil: Perfil){
        miDAO.borrarPerfil(miPerfil)
    }
    fun buscarPerfilPorId(id:Int): Flow<Perfil> {
        return miDAO.buscarPerfilPorId(id)
    }

    fun mostrarPreguntas(): Flow<List<Pregunta>> {
        return miDAO.mostrarPreguntas()
    }
    @WorkerThread
    suspend fun insertarPregunta(miPregunta: Pregunta){
        miDAO.insertarPregunta(miPregunta)
    }
    @WorkerThread
    suspend fun borrarPregunta(miPregunta: Pregunta){
        miDAO.borrarPregunta(miPregunta)
    }
    fun buscarPreguntaPorId(id:Int): Flow<Pregunta> {
        return miDAO.buscarPreguntaPorId(id)
    }
}