package com.example.trivial

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trivial.modelo.Pregunta
import com.example.trivial.databinding.FragmentTrivialBinding

class TrivialFragment : Fragment() {
    private var _binding: FragmentTrivialBinding? = null

    private val binding get() = _binding!!
    lateinit var miPregunta: Pregunta
    var idPregunta: Int = 0
    private var correctAnswerPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrivialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val puntos: SharedPreferences = (activity as MainActivity).getSharedPreferences("puntos", Context.MODE_PRIVATE)

        idPregunta = arguments?.getInt("id") ?: 0

        binding.bR1.setOnClickListener { siguiente(0, puntos) }
        binding.bR2.setOnClickListener { siguiente(1, puntos) }
        binding.bR3.setOnClickListener { siguiente(2, puntos) }
        binding.bR4.setOnClickListener { siguiente(3, puntos) }

        preguntaNueva()
    }

    fun preguntaNueva() {
        (activity as MainActivity).preguntasVM.buscarPreguntaPorId(idPregunta)
        (activity as MainActivity).preguntasVM.pregunta.observe(viewLifecycleOwner) { pregunta ->
            miPregunta = pregunta
            binding.etPregunta.setText(miPregunta.pregunta)

            val respuestas = listOf(
                miPregunta.respuesta1,
                miPregunta.respuesta2,
                miPregunta.respuesta3,
                miPregunta.correcta
            ).shuffled()

            correctAnswerPosition = respuestas.indexOf(miPregunta.correcta)

            binding.bR1.setText(respuestas[0])
            binding.bR2.setText(respuestas[1])
            binding.bR3.setText(respuestas[2])
            binding.bR4.setText(respuestas[3])
        }
    }

    fun siguiente(respuesta: Int, puntos: SharedPreferences) {
        val editor = puntos.edit()
        if (respuesta == correctAnswerPosition) {
            val currentPoints = puntos.getInt("puntos", 0)
            editor.putInt("puntos", currentPoints + 1)
            editor.apply()
        }

        idPregunta++
        preguntaNueva()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
