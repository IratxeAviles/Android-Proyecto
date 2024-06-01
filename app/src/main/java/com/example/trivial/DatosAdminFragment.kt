package com.example.trivial

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.trivial.modelo.Pregunta
import android.widget.Toast
import com.example.trivial.databinding.FragmentDatosadminBinding


class DatosAdminFragment : Fragment() {
    private var _binding: FragmentDatosadminBinding? = null

    private val binding get() = _binding!!
    lateinit var miPregunta: Pregunta
    var idPregunta: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDatosadminBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idPregunta = arguments?.getInt("id") ?: -1

        val admin: SharedPreferences = (activity as MainActivity).getSharedPreferences(
            "isAdmin",
            Context.MODE_PRIVATE
        )
        val isAdmin = admin.getBoolean("admin", false)

        if (isAdmin) {
            binding.bInsertar.isVisible
            binding.bModificar.isVisible
            binding.bBorrar.isVisible
        } else {
            binding.bInsertar.isVisible = false
            binding.bModificar.isVisible = false
            binding.bBorrar.isVisible = false
        }

        if (idPregunta == -1) {
            binding.bBorrar.isEnabled = false
            binding.bModificar.isEnabled = false
            binding.bInsertar.isEnabled = true
        } else {
            binding.bBorrar.isEnabled = true
            binding.bModificar.isEnabled = true
            binding.bInsertar.isEnabled = false

            (activity as MainActivity).preguntasVM.buscarPreguntaPorId(idPregunta)
            (activity as MainActivity).preguntasVM.pregunta.observe(activity as MainActivity) {
                miPregunta = it
                binding.etPregunta.setText(miPregunta.pregunta)
                binding.etR1.setText(miPregunta.respuesta1)
                binding.etR2.setText(miPregunta.respuesta2)
                binding.etR3.setText(miPregunta.respuesta3)
                binding.etCorrecta.setText(miPregunta.correcta)
            }

        }

        binding.bInsertar.setOnClickListener {
            if (validarContenido()) guardar()
        }
        binding.bModificar.setOnClickListener {
            if (validarContenido()) modificar()

        }
        binding.bBorrar.setOnClickListener {
            borrar()
        }

    }

    fun guardar() {
        (activity as MainActivity).preguntasVM.insertarPregunta(
            Pregunta(
                pregunta = binding.etPregunta.text.toString(),
                respuesta1 = binding.etR1.text.toString(),
                respuesta2 = binding.etR2.text.toString(),
                respuesta3 = binding.etR3.text.toString(),
                correcta = binding.etCorrecta.text.toString(),
            )
        )
        Toast.makeText(activity, "Pregunta insertada", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_datosFragment_to_firstFragment)
    }

    fun modificar() {
        (activity as MainActivity).preguntasVM.modificarPregunta(
            Pregunta(
                pregunta = binding.etPregunta.text.toString(),
                respuesta1 = binding.etR1.text.toString(),
                respuesta2 = binding.etR2.text.toString(),
                respuesta3 = binding.etR3.text.toString(),
                correcta = binding.etCorrecta.text.toString(),
            )
        )
        Toast.makeText(activity, "Pelicula modificada", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_datosFragment_to_firstFragment)

    }

    fun borrar() {
        (activity as MainActivity).preguntasVM.borrarPregunta(miPregunta)
        Toast.makeText(activity, "Pelicula eliminada", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_datosFragment_to_firstFragment)
    }

    fun validarContenido(): Boolean {
        if (binding.etPregunta.text.isEmpty() or
            binding.etR1.text.isEmpty() or
            binding.etR2.text.isEmpty() or
            binding.etR3.text.isEmpty() or
            binding.etCorrecta.text.isEmpty()
        ) {
            Toast.makeText(activity, "Hay que rellenar todos los datos", Toast.LENGTH_LONG).show()
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