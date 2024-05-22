package com.example.trivial.bbdd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trivial.modelo.Perfil
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {
    @Query("SELECT * FROM tabla_perfiles ORDER BY id ASC")
    fun mostrarPerfiles(): Flow<List<Perfil>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarPerfil(miPerfil: Perfil)

    @Delete
    suspend fun borrarPerfil(miPerfil: Perfil)

    @Update
    suspend fun modificarPerfil(miPerfil: Perfil)

    @Query("SELECT * FROM tabla_perfiles where id like :id")
    fun buscarPerfilPorId(id: Int): Flow<Perfil>
}

/*
@Query("SELECT * FROM puntuaciones ORDER BY puntuacion ASC")
    fun mostrarPuntuaciones(): Flow<List<Puntuacion>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(miPuntuacion: Puntuacion)

    @Delete
    suspend fun borrar(miPuntuacion: Puntuacion)

    @Update
    suspend fun modificar(miPuntuacion: Puntuacion)

    @Query("SELECT * FROM Puntuaciones where id like :id")
    fun buscarPorPerfil(id: Int): Flow<Perfil>
@Dao
interface DAO {

}
 */