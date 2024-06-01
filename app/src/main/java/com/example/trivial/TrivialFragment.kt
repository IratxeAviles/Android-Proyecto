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


class DatosadminFragment : Fragment() {
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

        (activity as MainActivity).preguntasVM.buscarPreguntaPorId(idPregunta)
        (activity as MainActivity).preguntasVM.pregunta.observe(activity as MainActivity) {
            miPregunta = it
            binding.etPregunta.setText(miPregunta.pregunta)
            binding.etR1.setText(miPregunta.respuesta1)
            binding.etR2.setText(miPregunta.respuesta2)
            binding.etR3.setText(miPregunta.respuesta3)
            binding.etR4.setText(miPregunta.correcta)
        }

        binding.b.setOnClickListener {

        }
        binding.bModificar.setOnClickListener {

        }
        binding.bBorrar.setOnClickListener {

        }
        binding.bBorrar.setOnClickListener {

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}