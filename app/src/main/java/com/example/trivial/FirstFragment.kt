package com.example.trivial

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.trivial.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datos: SharedPreferences =
            (activity as MainActivity).getSharedPreferences("datos", Context.MODE_PRIVATE)
        if (datos.getString("usuario", "")?.isNotEmpty() ?: false &&
            datos.getString("password", "")?.isNotEmpty() ?: false
        ) {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_fragment_first, menu)
            }


            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.m_Iniciar -> {
                        inicioSesion()
                        true
                    }

                    R.id.m_Cerrar -> {
                        cerrarSesion(datos)
                        true
                    }

                    else -> false
                }
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                menu.findItem(R.id.m_volver).isVisible = false

                val isLoggedIn = comprobarSesion()
                menu.findItem(R.id.m_Iniciar).isVisible = !isLoggedIn
                menu.findItem(R.id.m_LogOut).isVisible = isLoggedIn
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        if ((activity as MainActivity).admin){
            binding.tBienvenida.text = "¡Bienvenid@ Admin!"
            binding.bPreguntas.isVisible = true
        } else {
            binding.tBienvenida.text = "¡Bienvenid@ a Trivial!"
            binding.bPreguntas.isVisible = false
        }

        binding.bJugar.setOnClickListener {
            if (comprobarSesion()) {
                findNavController().navigate(R.id.action_FirstFragment_to_thirdFragment)
            } else {
                Toast.makeText(activity, "No has iniciado sesion", Toast.LENGTH_LONG).show()
            }
        }

        binding.bPuntuacion.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_fourthFragment)
        }
    }

    private fun comprobarSesion(): Boolean {
        val datos: SharedPreferences =
            (activity as MainActivity).getSharedPreferences("datos", Context.MODE_PRIVATE)

        return datos.getBoolean("is_logged_in", false)
    }

    fun inicioSesion() {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    fun cerrarSesion(datos: SharedPreferences) {
        val editor: SharedPreferences.Editor = datos.edit()
        editor.putString("usuario", "")
        editor.putString("password", "")
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}