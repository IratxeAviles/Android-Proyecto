package com.example.trivial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.trivial.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private var errores: String = ""
    private var codigo: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bSesion.setOnClickListener {

            if (validar() == "") {
                if (binding.etUsuario.text.toString() == "admin" && binding.etContrasena.text.toString() == "12345Abcde") {
                    (activity as MainActivity).admin = true
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                } else {
                    Toast.makeText(
                        activity,
                        "Usuario y/o contraseña incorrectos",
                        Toast.LENGTH_LONG
                    ).show()
                }
                /*
                (activity as MainActivity).puntuacionesVM.mostrarPuntuaciones()
                (activity as MainActivity).puntuacionesVM.listaPuntuaciones.observe(activity as MainActivity) {
                    for (puntuacion in it) {
                        if (puntuacion.usuario == binding.etUsuario.text.toString()) {
                            codigo = puntuacion.id
                            break
                        }
                    }
                    if (codigo == 0) {
                        // crear perfil
                        Toast.makeText(activity, "Usuario no encontrado", Toast.LENGTH_LONG).show()
                    } else {
                        // iniciar sesion con el perfil
                        val editor: SharedPreferences.Editor = datos.edit()
                        editor.putString("usuario", binding.etUsuario.text.toString())
                        editor.putString("contrasena", binding.etContrasena.text.toString())
                        editor.putInt("id", codigo)
                        editor.apply()
                        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                    }
                }
                 */

            } else {
                Toast.makeText(activity, errores, Toast.LENGTH_LONG).show()
            }
            errores = ""
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_fragment_second, menu)
                menu.findItem(R.id.m_LogOut).isVisible = false
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    // R.id.mf_mi_insertar -> {
                    //     insertar()
                    //     true
                    // }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun validar(): String {
        for (i in 0 until binding.llPrincipal.childCount) {
            val view = binding.llPrincipal.getChildAt(i)
            if (view is EditText) {
                if (view.text.toString().isEmpty()) {
                    errores = "${errores}\n ${view.hint} esta vacio"
                }
            }
        }
        return errores
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}