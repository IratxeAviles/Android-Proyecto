package com.example.trivial

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.trivial.databinding.FragmentFirstBinding
import com.example.trivial.modelo.Pregunta
import com.example.trivial.modelo.Puntuacion

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

        val administrador: SharedPreferences =
            (activity as MainActivity).getSharedPreferences("isAdmin", Context.MODE_PRIVATE)
        val isAdmin = administrador.getBoolean("isAdmin", false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_fragment_first, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.m_Iniciar -> {
                        findNavController().navigate(R.id.action_firstFragment_to_loginFragment)
                        true
                    }
                    R.id.m_Cerrar -> {
                        (activity as MainActivity).cerrarSesion()
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
            binding.bJugar.isVisible = false
            binding.bPuntuacion.isVisible = false
            binding.bNuevaPregunta.isVisible = true
            binding.bModificarPregunta.isVisible = true
        } else {
            binding.tBienvenida.text = "¡Bienvenid@ a Trivial!"
            binding.bJugar.isVisible = true
            binding.bPuntuacion.isVisible = true
            binding.bNuevaPregunta.isVisible = false
            binding.bModificarPregunta.isVisible = false
        }

        binding.bNuevaPregunta.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_nuevaPreguntaFragment)
        }

        binding.bModificarPregunta.setOnClickListener {
            mostrarPreguntasYActuar { preguntas ->
                if (preguntas.isNotEmpty()) {
                    findNavController().navigate(R.id.action_firstFragment_to_datosFragment)
                } else {
                    Toast.makeText(activity, "No hay preguntas disponibles", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.bJugar.setOnClickListener {
            mostrarPreguntasYActuar { preguntas ->
                if (preguntas.isNotEmpty()) {
                    findNavController().navigate(R.id.action_firstFragment_to_trivialFragment)
                } else {
                    Toast.makeText(activity, "No hay preguntas disponibles", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.bPuntuacion.setOnClickListener {
            mostrarPuntuacionesYActuar { puntuaciones ->
                if (puntuaciones.isNotEmpty()) {
                    findNavController().navigate(R.id.action_firstFragment_to_puntuacionesFragment)
                } else {
                    Toast.makeText(activity, "No hay puntuaciones disponibles", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun mostrarPreguntasYActuar(callback: (List<Pregunta>) -> Unit) {
        (activity as MainActivity).preguntasVM.mostrarPreguntas()
        (activity as MainActivity).preguntasVM.listaPreguntas.observe(viewLifecycleOwner, Observer { preguntas ->
            callback(preguntas)
        })
    }
    private fun mostrarPuntuacionesYActuar(callback: (List<Puntuacion>) -> Unit) {
        (activity as MainActivity).puntuacionesVM.mostrarPuntuaciones()
        (activity as MainActivity).puntuacionesVM.listaPuntuaciones.observe(viewLifecycleOwner, Observer { puntuaciones ->
            callback(puntuaciones)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
