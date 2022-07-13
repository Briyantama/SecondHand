package com.binar.secondhand.ui.akun

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.databinding.FragmentAkunBinding
import com.google.android.material.snackbar.Snackbar

class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences : SharedPreferences = requireActivity()
            .getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)

        binding.keluar.setOnClickListener {
            val dialog = AlertDialog.Builder(view.context)
            dialog.setTitle("Logout")
            dialog.setMessage("Apakah Anda Yakin Ingin Logout ?")
            dialog.setPositiveButton("Yakin") { _, _ ->
                Snackbar.make(binding.root, "User Berhasil Logout", Snackbar.LENGTH_LONG)
                    .show()
                val sharedEditor = sharedPreferences.edit()
                sharedEditor.putInt("login",0)
                sharedEditor.apply()
                Navigation.findNavController(requireView()).navigate(R.id.action_navigation_akun_to_loginFragment)
            }
            dialog.setNegativeButton("Batal") { listener, _ ->
                listener.dismiss()
            }
            dialog.show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}