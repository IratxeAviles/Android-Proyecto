package com.example.trivial.BBDD

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class Room {
    @Entity(tableName = "puntuaciones")
    data class Puntuacion(
        @PrimaryKey(autoGenerate = true) var id:Int=0,
        @NonNull @ColumnInfo(name = "puntuacion") val puntuacion:Int=0) {}

    @Entity(tableName = "perfiles")
    data class Perfil(
        @PrimaryKey(autoGenerate = true) var id:Int=0,
        @NonNull @ColumnInfo(name = "perfil") val perfil:Int=0) {}
}