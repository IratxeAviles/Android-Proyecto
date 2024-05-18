package com.example.trivial.modelo

class Puntuacion(var id:Int, var usuario:String, var puntuacion:Int) {
    // No se si el id lo necesito
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Puntuacion

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}