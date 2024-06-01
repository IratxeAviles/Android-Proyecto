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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trivial.databinding.FragmentPuntuacionesBinding
import com.example.trivial.recyclerview.Adaptador


class PuntuacionesFragment : Fragment() {
    private var _binding: FragmentPuntuacionesBinding? = null
    private val binding get() = _binding!!
    lateinit var miRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPuntuacionesBinding.inflate(inflater, container, false)
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

        (activity as MainActivity).puntuacionesVM.mostrarPuntuaciones()
        (activity as MainActivity).puntuacionesVM.listaPuntuaciones.observe(activity as MainActivity) {
            if (it != null) {
                miRecyclerView = binding.rvPuntuaciones
                miRecyclerView.layoutManager = LinearLayoutManager(activity)
                miRecyclerView.adapter = Adaptador(it)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).puntuacionesVM.listaPuntuaciones.removeObservers(activity as MainActivity)
    }
}