package com.example.trivial

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.trivial.modelo.Pregunta
import com.example.trivial.databinding.FragmentTrivialBinding

class TrivialFragment : Fragment() {
    private var _binding: FragmentTrivialBinding? = null

    private val binding get() = _binding!!
    lateinit var miPregunta: Pregunta
    var posPregunta: Int = 0
    lateinit var listaPreguntas: LiveData<List<Pregunta>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrivialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val puntos: SharedPreferences =
            (activity as MainActivity).getSharedPreferences("puntos", Context.MODE_PRIVATE)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_fragment_fourth, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        mostrar()

        binding.bR1.setOnClickListener { siguiente(binding.bR1.text.toString(), puntos) }
        binding.bR2.setOnClickListener { siguiente(binding.bR2.text.toString(), puntos) }
        binding.bR3.setOnClickListener { siguiente(binding.bR3.text.toString(), puntos) }
        binding.bR4.setOnClickListener { siguiente(binding.bR4.text.toString(), puntos) }


    }

    fun mostrar() {
        listaPreguntas.observe(viewLifecycleOwner) { preguntas ->
            if (preguntas.isNotEmpty()) {
                try {
                    (activity as MainActivity).preguntasVM.buscarPreguntaPorId(preguntas[posPregunta].id)
                    (activity as MainActivity).preguntasVM.pregunta.observe(activity as MainActivity) {

                        miPregunta = it
                        binding.etPregunta.setText(miPregunta.pregunta)

                        val respuestas = listOf(
                            miPregunta.respuesta1,
                            miPregunta.respuesta2,
                            miPregunta.respuesta3,
                            miPregunta.correcta
                        ).shuffled()

                        binding.bR1.setText(respuestas[0])
                        binding.bR2.setText(respuestas[1])
                        binding.bR3.setText(respuestas[2])
                        binding.bR4.setText(respuestas[3])
                    }
                } catch (e: Exception) {
                    Toast.makeText(activity, "No puedes hacer eso", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "No hay preguntas disponibles", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_datosFragment_to_firstFragment)
            }
        }
    }

    fun siguiente(respuesta: String, puntos: SharedPreferences) {
        val editor = puntos.edit()
        if (respuesta == miPregunta.correcta) {
            val currentPoints = puntos.getInt("puntos", 0)
            editor.putInt("puntos", currentPoints + 1)
            editor.apply()
        }
        posPregunta += 1
        mostrar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
