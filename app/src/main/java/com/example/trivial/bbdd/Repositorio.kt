package com.example.trivial.bbdd

import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trivial.modelo.Perfil
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
    @WorkerThread
    suspend fun modificarPerfil(miPerfil: Perfil){
        miDAO.modificarPerfil(miPerfil)
    }
    fun buscarPerfilPorId(id:Int): Flow<Perfil> {
        return miDAO.buscarPerfilPorId(id)
    }
}


/*
@Entity(tableName = "puntuaciones")
    data class Puntuacion(
        @PrimaryKey(autoGenerate = true) var id:Int=0,
        @NonNull @ColumnInfo(name = "puntuacion") val puntuacion:Int=0) {}

    @Entity(tableName = "perfiles")
    data class Perfil(
        @PrimaryKey(autoGenerate = true) var id:Int=0,
        @NonNull @ColumnInfo(name = "perfil") val perfil:Int=0) {}
 */