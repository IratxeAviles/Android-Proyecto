package com.example.trivial

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.example.trivial.modelo.Perfil

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private var errores: String=""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datos: SharedPreferences =(activity as MainActivity).getSharedPreferences("datos",
            Context.MODE_PRIVATE)

        binding.bSesion.setOnClickListener {
            if(validar()==""){
                val editor: SharedPreferences.Editor =datos.edit()
                editor.putString("usuario", binding.etUsuario.text.toString())
                editor.putString("password",binding.etContrasena.text.toString())
                editor.apply()
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            } else {
                Toast.makeText(activity,errores, Toast.LENGTH_LONG).show()
            }
            errores=""
        }
        binding.bRegistro.setOnClickListener {
            sesion()
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_fragment_second, menu)
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

        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun validar():String{
        for (i in 0 until binding.llPrincipal.childCount) {
            val view = binding.llPrincipal.getChildAt(i)
            if (view is EditText) {
                if (view.text.toString().isEmpty()){
                    errores= "${errores}\n ${view.hint} esta vacio"
                }
            }
        }
        return errores
    }

    fun sesion(){

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}