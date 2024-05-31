package com.example.trivial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trivial.databinding.FragmentFourthBinding


class PuntuacionesFragment : Fragment() {
    private var _binding: FragmentFourthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var miRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        (activity as MainActivity).miViewModel.mostrarPuntuaciones() // seria con puntuaciones, pero para probar
        (activity as MainActivity).miViewModel.listaPuntuaciones.observe(activity as MainActivity) {
            if(it!=null){
                miRecyclerView = binding.rvPuntuaciones
                miRecyclerView.layoutManager = LinearLayoutManager(activity)
                miRecyclerView.adapter = Adaptador(it)
            }

        }

 */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}