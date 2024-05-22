package com.example.trivial.modelo

import androidx.lifecycle.ViewModel

class VM: ViewModel() {
    var usuario: Perfil?=null
    var preguntas: MutableList<Pregunta> = mutableListOf()
    var aciertos: Pregunta?=null // no se si asi o siendo int
}