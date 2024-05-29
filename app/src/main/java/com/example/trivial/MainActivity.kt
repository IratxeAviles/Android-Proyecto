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
import com.example.trivial.modelo.Perfil
import com.example.trivial.modelo.Pregunta
import com.example.trivial.modelo.PerfilesVM
import com.example.trivial.modelo.PerfilesViewModelFactory
import com.example.trivial.modelo.PreguntasVM
import com.example.trivial.modelo.PreguntasViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    val miDataBase by lazy { BBDD.getDatabase(this)}
    val miRepositorio by lazy { Repositorio(miDataBase.miDAO()) }
    val preguntasVM: PreguntasVM by viewModels { PreguntasViewModelFactory(miRepositorio) }
    val perfilesVM: PerfilesVM by viewModels { PerfilesViewModelFactory(miRepositorio) }
    var puntuaciones: MutableList<Perfil> = mutableListOf()

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
        val datos: SharedPreferences =this.getSharedPreferences("datos",
            Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =datos.edit()
        editor.putString("usuario","")
        editor.putString("contrasena","")
        editor.apply()
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.popBackStack(R.id.FirstFragment, false)
        navController.navigate(R.id.FirstFragment)

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}