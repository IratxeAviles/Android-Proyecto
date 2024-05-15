package com.example.trivial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.trivial.databinding.FragmentFirstBinding
import com.google.android.material.navigation.NavigationView

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_fragment_first, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.m_Sesion->{
                        InicioSesion()
                        true
                    }
                    else->false
                }
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                menu.findItem(R.id.m_volver).isVisible=false
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)

        (activity as MainActivity).perfil?.let{
            binding.tBienvenida.text="Bienvenid@ ${it.usuario}"
        }

        binding.bJugar.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_thirdFragment)
        }

        binding.bPuntuacion.setOnClickListener {
            //puntuacion()
        }
    }

    fun InicioSesion() {
        //if (sesion) {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        //} else {

        //}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}