package com.example.trivial.bbdd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trivial.modelo.Perfil
import com.example.trivial.modelo.Pregunta

@Database(entities = arrayOf(Perfil::class, Pregunta::class), version = 1, exportSchema = false)
abstract class BBDD: RoomDatabase() {
    abstract fun miDAO():DAO
    companion object{
        @Volatile
        private var INSTANCE:BBDD?=null
        fun getDatabase(context: Context):BBDD{
            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    BBDD::class.java,
                    "puntuacion_dataBase"
                ).build()
                INSTANCE=instance
                instance }}}}