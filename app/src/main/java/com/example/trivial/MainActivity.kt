package com.example.trivial

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
import com.example.trivial.modelo.Puntuacion
import com.example.trivial.modelo.PerfilesVM
import com.example.trivial.modelo.PerfilesViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    val miDataBase by lazy { BBDD.getDatabase(this)}
    val miRepositorio by lazy { Repositorio(miDataBase.miDAO()) }
    val miViewModel: PerfilesVM by viewModels { PerfilesViewModelFactory(miRepositorio) }
    var puntuaciones: MutableList<Puntuacion> = mutableListOf()

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
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.m_volver -> {
                findNavController(R.id.nav_host_fragment_content_main).popBackStack()
                true
            }
            else -> false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}