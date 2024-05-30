package com.example.trivial.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity (tableName = "tabla_puntuaciones")
data class Puntuacion(
    @PrimaryKey(autoGenerate = true) val id:Int=1,
    @NotNull @ColumnInfo var usuario: String,
    @NotNull @ColumnInfo var record: Int = 0,
) {


}