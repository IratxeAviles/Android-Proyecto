package com.example.trivial.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tabla_preguntas")
data class Pregunta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @NotNull @ColumnInfo (name = "pregunta") var pregunta: String,
    @NotNull @ColumnInfo (name = "respuesta1")var respuesta1: String,
    @NotNull @ColumnInfo (name = "respuesta2")var respuesta2: String,
    @NotNull @ColumnInfo (name = "respuesta3")var respuesta3: String,
    @NotNull @ColumnInfo (name = "correcta") var correcta: String,
) {


}