package com.example.trivial.BBDD

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trivial.modelo.Perfil
import com.example.trivial.modelo.Puntuacion
import kotlinx.coroutines.flow.Flow

class DAO {
    @Dao
    interface PuntuacionDAO {
        @Query("SELECT * FROM puntuaciones ORDER BY puntuacion ASC")
        fun mostrarPuntuaciones(): Flow<List<Puntuacion>>
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insertar(miPuntuacion: Puntuacion)
        @Delete
        suspend fun borrar(miPuntuacion: Puntuacion)
        @Update
        suspend fun modificar(miPuntuacion: Puntuacion)
        @Query("SELECT * FROM Puntuaciones where id like :id")
        fun buscarPorPerfil(id:Int): Flow<Perfil>
    }

    @Dao
    interface PerfilDAO {
        @Query("SELECT * FROM perfiles ORDER BY perfil ASC")
        fun mostrarPerfiles(): Flow<List<Puntuacion>>
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insertar(miPerfil: Perfil)
        @Delete
        suspend fun borrar(miPerfil: Perfil)
        @Update
        suspend fun modificar(miPerfil: Perfil)
        @Query("SELECT * FROM Perfiles where id like :id")
        fun buscarPorPerfil(id:Int): Flow<Perfil>
    }
}