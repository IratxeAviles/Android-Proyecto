package com.example.trivial

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.trivial.bbdd.BBDD
import com.example.trivial.bbdd.Repositorio
import com.example.trivial.databinding.ActivityMainBinding
import com.example.trivial.modelo.Pregunta
import com.example.trivial.modelo.PreguntasVM
import com.example.trivial.modelo.PreguntasViewModelFactory
import com.example.trivial.modelo.Puntuacion
import com.example.trivial.modelo.PuntuacionesVM
import com.example.trivial.modelo.PuntuacionesViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    val miDataBase by lazy { BBDD.getDatabase(this) }
    val miRepositorio by lazy { Repositorio(miDataBase.miDAO()) }
    val preguntasVM: PreguntasVM by viewModels { PreguntasViewModelFactory(miRepositorio) }
    val puntuacionesVM: PuntuacionesVM by viewModels { PuntuacionesViewModelFactory(miRepositorio) }
    val puntos:SharedPreferences=this.getSharedPreferences("puntos",Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.m_volver -> {
                findNavController(R.id.nav_host_fragment_content_main).popBackStack()
                true
            }

            R.id.m_LogOut -> {
                cerrarSesion()
                true
            }

            else -> super.onOptionsItemSelected(item)


        }
    }

    fun cerrarSesion() {
        val administrador: SharedPreferences = this.getSharedPreferences("isAdmin", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = administrador.edit()
        editor.putBoolean("isAdmin", false)
        editor.apply()
        findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.firstFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}