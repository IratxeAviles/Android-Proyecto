package com.example.trivial.bbdd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.trivial.modelo.Pregunta
import com.example.trivial.modelo.Puntuacion

@Database(entities = arrayOf(Puntuacion::class, Pregunta::class), version = 1, exportSchema = false)
abstract class BBDD : RoomDatabase() {
    abstract fun miDAO(): DAO

    companion object {
        @Volatile
        private var INSTANCE: BBDD? = null

        /*
        private val roomCallBack = object: RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase){
                super.onCreate(db)
                // Insertar los datos iniciales
                val preguntasIniciales = listOf(
                    Pregunta(1, "Pregunta1","Respuesta1","Respuesta2","Respuesta3","Correcta"),
                    Pregunta(2, "Pregunta1","Respuesta1","Respuesta2","Respuesta3","Correcta"),
                    Pregunta(3, "Pregunta1","Respuesta1","Respuesta2","Respuesta3","Correcta"),
                    )
            }
        }
         */
        fun getDatabase(context: Context): BBDD {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BBDD::class.java,
                    "app_dataBase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}