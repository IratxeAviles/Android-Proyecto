package com.example.trivial

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.trivial.databinding.FragmentFirstBinding

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
                return when (menuItem.itemId) {
                    R.id.m_Iniciar -> {
                        InicioSesion()
                        true
                    }

                    R.id.m_Cerrar -> {
                        CerrarSesion()
                        true
                    }

                    else -> false
                }
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                menu.findItem(R.id.m_volver).isVisible = false

                val isLoggedIn = ComprobarSesion()
                menu.findItem(R.id.m_Iniciar).isVisible = !isLoggedIn
                menu.findItem(R.id.m_Cerrar).isVisible = isLoggedIn

                // Para aplicar el color blanco a la letra del los menu item
                for (i in 0 until menu.size()) {
                    val menuItem = menu.getItem(i)
                    val spannableTitle = SpannableString(menuItem.title)
                    spannableTitle.setSpan(ForegroundColorSpan(Color.WHITE), 0, spannableTitle.length, 0)
                    menuItem.title = spannableTitle
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        (activity as MainActivity).miViewModel.usuario?.let {
            binding.tBienvenida.text = "¡Bienvenid@ ${it.usuario}!"
        }

        binding.bJugar.setOnClickListener {
            if (ComprobarSesion()){
                findNavController().navigate(R.id.action_FirstFragment_to_thirdFragment)
            } else {
                Toast.makeText(activity,"No has iniciado sesion", Toast.LENGTH_LONG).show()
            }
        }

        binding.bPuntuacion.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_fourthFragment)
        }
    }

    private fun ComprobarSesion(): Boolean {
        //val sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        //return sharedPreferences.getBoolean("is_logged_in", false)
        if ((activity as MainActivity).miViewModel.usuario == null) {
            return false
        } else {
            return true
        }
    }

    fun InicioSesion() {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    fun CerrarSesion() {
        (activity as MainActivity).miViewModel.usuario = null
        binding.tBienvenida.text = "Inicia sesión para poder jugar"
        val sharedPreferences =
            requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("is_logged_in", false)
            apply()
        }
        activity?.invalidateOptionsMenu()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}