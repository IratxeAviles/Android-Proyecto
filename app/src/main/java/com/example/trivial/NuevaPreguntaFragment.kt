package com.example.trivial

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.trivial.modelo.Pregunta
import android.widget.Toast
import com.example.trivial.databinding.FragmentNuevaPreguntaBinding


class NuevaPreguntaFragment : Fragment() {
    private var _binding: FragmentNuevaPreguntaBinding? = null

    private val binding get() = _binding!!
    lateinit var miPregunta: Pregunta
    var idPregunta: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNuevaPreguntaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idPregunta = arguments?.getInt("id") ?: -1


        binding.bInsertar.setOnClickListener {
            if (validarContenido()) guardar()
        }
        binding.bCancelar.setOnClickListener {
            findNavController().navigate(R.id.action_nuevaPreguntaFragment_to_firstFragment)
        }

    }

    fun guardar() {
        try {
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
            findNavController().navigate(R.id.action_nuevaPreguntaFragment_to_firstFragment)
        } catch (e: Exception) {
            Toast.makeText(
                (activity as MainActivity), "Error al insertar la pregunta",
                Toast.LENGTH_SHORT
            ).show()
            print(e)
        }
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