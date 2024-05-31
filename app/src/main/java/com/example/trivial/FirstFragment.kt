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

        val admin:SharedPreferences=(activity as MainActivity).getSharedPreferences("isAdmin",Context.MODE_PRIVATE)
        val isAdmin = admin.getBoolean("admin", false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_fragment_first, menu)
            }


            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.m_Iniciar -> {
                        inicioSesion(admin)
                        true
                    }

                    R.id.m_Cerrar -> {
                        cerrarSesion(admin)
                        true
                    }

                    else -> false
                }
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)


                menu.findItem(R.id.m_volver).isVisible = false

                menu.findItem(R.id.m_Iniciar).isVisible = !isAdmin
                menu.findItem(R.id.m_LogOut).isVisible = isAdmin
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        if (isAdmin) {
            binding.tBienvenida.text = "¡Bienvenid@ Admin!"
        } else {
            binding.tBienvenida.text = "¡Bienvenid@ a Trivial!"
        }

        binding.bJugar.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_datosFragment)
        }

        binding.bPuntuacion.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_puntuacionesFragment)
        }
    }

    fun inicioSesion(admin: SharedPreferences) {
        val editor: SharedPreferences.Editor = admin.edit()
        editor.putBoolean("admin", true)
        editor.apply()
        findNavController().navigate(R.id.action_firstFragment_to_loginFragment)
    }

    fun cerrarSesion(admin: SharedPreferences) {
        val editor: SharedPreferences.Editor = admin.edit()
        editor.putBoolean("admin", false)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}