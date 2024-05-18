package com.example.trivial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio6.recyclerView.Adaptador
import com.example.trivial.databinding.FragmentFourthBinding
import com.example.trivial.modelo.Puntuacion


class FourthFragment : Fragment() {
    private var _binding: FragmentFourthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPuntuaciones.layoutManager = LinearLayoutManager(activity as MainActivity)
        binding.rvPuntuaciones.adapter = Adaptador((activity as MainActivity).puntuaciones)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}