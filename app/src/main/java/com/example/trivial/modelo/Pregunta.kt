package com.example.trivial.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tabla_preguntas")
data class Pregunta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @NotNull @ColumnInfo (name = "pregunta") var pregunta: String,
    @NotNull @ColumnInfo (name = "respuestas")var respuestas: Array<String>,
    @NotNull @ColumnInfo (name = "correcta") var correcta: Int,
) {


}