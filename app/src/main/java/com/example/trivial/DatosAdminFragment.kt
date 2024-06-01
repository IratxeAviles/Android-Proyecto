package com.example.trivial

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.trivial.modelo.Pregunta
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trivial.databinding.FragmentDatosadminBinding
import com.example.trivial.recyclerview.Adaptador


class DatosAdminFragment : Fragment() {
    private var _binding: FragmentDatosadminBinding? = null

    private val binding get() = _binding!!
    lateinit var miPregunta: Pregunta
    var posPregunta: Int = 0
    lateinit var listaPreguntas: LiveData<List<Pregunta>>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDatosadminBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        binding.bModificar.setOnClickListener {
            if (validarContenido()) {
                modificar()
            }
        }
        binding.bBorrar.setOnClickListener {
            borrar()
            mostrar()
        }
        binding.bSiguiente.setOnClickListener {
            posPregunta += 1
        }
    }

    fun actualizarLista() {
        (activity as MainActivity).preguntasVM.mostrarPreguntas()
        listaPreguntas = (activity as MainActivity).preguntasVM.listaPreguntas
    }


    fun mostrar() {
        actualizarLista()

        listaPreguntas.observe(viewLifecycleOwner) { preguntas ->
            if (preguntas.isNotEmpty()) {
                (activity as MainActivity).preguntasVM.buscarPreguntaPorId(preguntas[posPregunta].id)
                (activity as MainActivity).preguntasVM.pregunta.observe(activity as MainActivity) {
                    miPregunta = it
                    binding.etPregunta.setText(miPregunta.pregunta)
                    binding.etR1.setText(miPregunta.respuesta1)
                    binding.etR2.setText(miPregunta.respuesta2)
                    binding.etR3.setText(miPregunta.respuesta3)
                    binding.etCorrecta.setText(miPregunta.correcta)
                }
            } else {
                Toast.makeText(activity, "No hay preguntas disponibles", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_datosFragment_to_firstFragment)
            }
        }
    }

    fun modificar() {
        (activity as MainActivity).preguntasVM.modificarPregunta(
            Pregunta(
                id = miPregunta.id,
                pregunta = binding.etPregunta.text.toString(),
                respuesta1 = binding.etR1.text.toString(),
                respuesta2 = binding.etR2.text.toString(),
                respuesta3 = binding.etR3.text.toString(),
                correcta = binding.etCorrecta.text.toString(),
            )
        )
        Toast.makeText(activity, "Pregunta modificada", Toast.LENGTH_LONG).show()
    }

    fun borrar() {
        (activity as MainActivity).preguntasVM.borrarPregunta(miPregunta)
        Toast.makeText(activity, "Pregunta eliminada", Toast.LENGTH_LONG).show()
    }

    fun validarContenido(): Boolean {
        if (binding.etPregunta.text.isEmpty() or binding.etR1.text.isEmpty() or binding.etR2.text.isEmpty() or binding.etR3.text.isEmpty() or binding.etCorrecta.text.isEmpty()) {
            Toast.makeText(activity, "Hay que rellenar todos los datos", Toast.LENGTH_LONG)
                .show()
            return false
        } else {
            return true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}