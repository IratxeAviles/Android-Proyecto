package com.example.trivial.bbdd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trivial.modelo.Pregunta
import com.example.trivial.modelo.Puntuacion
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {
    @Query("SELECT * FROM tabla_puntuaciones ORDER BY id ASC")
    fun mostrarPuntuaciones(): Flow<List<Puntuacion>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarPuntuacion(miPuntuacion: Puntuacion)

    @Delete
    suspend fun borrarPuntuacion(miPuntuacion: Puntuacion)

    @Update
    suspend fun modificarPuntuacion(miPuntuacion: Puntuacion)

    @Query("SELECT * FROM tabla_puntuaciones where id like :id")
    fun buscarPuntuacionPorId(id: Int): Flow<Puntuacion>

    @Query("SELECT * FROM tabla_preguntas ORDER BY id ASC")
    fun mostrarPreguntas(): Flow<List<Pregunta>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarPregunta(miPregunta: Pregunta)

    @Delete
    suspend fun borrarPregunta(miPregunta: Pregunta)

    @Update
    suspend fun modificarPerfil(miPregunta: Pregunta)

    @Query("SELECT * FROM tabla_preguntas where id like :id")
    fun buscarPreguntaPorId(id: Int): Flow<Pregunta>
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