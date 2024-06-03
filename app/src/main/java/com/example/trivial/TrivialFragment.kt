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
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.trivial.modelo.Pregunta
import com.example.trivial.databinding.FragmentTrivialBinding
import com.example.trivial.modelo.Puntuacion

class TrivialFragment : Fragment() {
    private var _binding: FragmentTrivialBinding? = null
    private val binding get() = _binding!!

    lateinit var miPregunta: Pregunta
    lateinit var miPuntuacion: Puntuacion
    var posPregunta: Int = 0
    lateinit var listaPreguntas: LiveData<List<Pregunta>>
    var puntos = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrivialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvPuntuacion.isVisible = false
        binding.etIntroducirNombre.isVisible = false
        binding.bGuardarPuntuacion.isVisible = false

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_fragment_third, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        (activity as MainActivity).preguntasVM.mostrarPreguntas()
        listaPreguntas = (activity as MainActivity).preguntasVM.listaPreguntas

        listaPreguntas.observe(viewLifecycleOwner, Observer { preguntas ->
            if (preguntas.isNotEmpty()) {
                mostrar(preguntas)
            } else {
                Toast.makeText(activity, "No hay preguntas disponibles", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_trivialFragment_to_firstFragment)
            }
        })

        binding.bR1.setOnClickListener { siguiente(binding.bR1.text.toString()) }
        binding.bR2.setOnClickListener { siguiente(binding.bR2.text.toString()) }
        binding.bR3.setOnClickListener { siguiente(binding.bR3.text.toString()) }
        binding.bR4.setOnClickListener { siguiente(binding.bR4.text.toString()) }
        binding.bGuardarPuntuacion.setOnClickListener { guardarPuntuacion() }
    }

    private fun mostrar(preguntas: List<Pregunta>) {
        if (posPregunta >= preguntas.size) {
            binding.etPregunta.setText("¡Has terminado!")
            binding.bR1.isVisible = false
            binding.bR2.isVisible = false
            binding.bR3.isVisible = false
            binding.bR4.isVisible = false
            juegoterminado()
            return
        }

        val pregunta = preguntas[posPregunta]
        miPregunta = pregunta
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

    private fun siguiente(respuesta: String) {
        if (respuesta == miPregunta.correcta) {
            puntos += 1
        }
        posPregunta += 1
        listaPreguntas.value?.let { mostrar(it) }
    }

    private fun juegoterminado() {
        binding.tvPuntuacion.setText("Puntuacion: $puntos")
        binding.tvPuntuacion.isVisible = true
        binding.etIntroducirNombre.isVisible = true
        binding.bGuardarPuntuacion.isVisible = true
    }

    private fun guardarPuntuacion() {
        if (binding.etIntroducirNombre.text.toString() != "") {
            (activity as MainActivity).puntuacionesVM.buscarPuntuacionPorNombre(binding.etIntroducirNombre.text.toString())
            (activity as MainActivity).puntuacionesVM.puntuacion.observe(activity as MainActivity) {

                try {
                    if (it != null) {
                        it.record = puntos
                        (activity as MainActivity).puntuacionesVM.modificarPuntuacion(it)
                        Toast.makeText((activity as MainActivity), "Puntuacion actualizada", Toast.LENGTH_LONG).show()
                    } else {
                        (activity as MainActivity).puntuacionesVM.insertarPuntuacion(
                            Puntuacion(
                                usuario = binding.etIntroducirNombre.text.toString(),
                                record = puntos,
                            )
                        )
                        Toast.makeText((activity as MainActivity), "Puntuacion insertada", Toast.LENGTH_LONG).show()
                    }
                    findNavController().navigate(R.id.action_trivialFragment_to_firstFragment)

                } catch (e: Exception) {
                    Toast.makeText((activity as MainActivity), e.message, Toast.LENGTH_SHORT).show()
                }

                //findNavController().navigate(R.id.action_trivialFragment_to_firstFragment)

            }
            /* try {

                 findNavController().navigate(R.id.action_trivialFragment_to_firstFragment)
             } catch (e: Exception) {
                 Toast.makeText(
                     (activity as MainActivity), "Error al insertar la puntuacion",
                     Toast.LENGTH_SHORT
                 ).show()
                 print(e)
             }*/
        } else {
            Toast.makeText((activity as MainActivity), "Nombre vacio", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).puntuacionesVM.puntuacion.removeObservers(activity as MainActivity)
    }
}
