package com.binar.secondhand.ui.akun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.binar.secondhand.R
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentAkunBinding
import com.binar.secondhand.helper.Sharedpref
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!
    private val viewModel : AkunViewModel by viewModel()
    private val sharedPref get() = Sharedpref(requireContext())

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

        val token = getKoin().getProperty("access_token", "")

        viewModel.getAuth()
        setUpAccount()

        binding.ubahAkun.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navigation_akun_to_ubahAkunFragment)
        }

        binding.pengaturan.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navigation_akun_to_changePassFragment)
        }

        binding.keluar.setOnClickListener {
            val dialog = AlertDialog.Builder(view.context)
            dialog.setTitle("Logout")
            dialog.setMessage("Apakah Anda Yakin Ingin Logout ?")
            dialog.setPositiveButton("Yakin") { _, _ ->
                Snackbar.make(binding.root, "User Berhasil Logout", Snackbar.LENGTH_LONG)
                    .show()
                sharedPref.clear()
                getKoin().setProperty("access_token","")
                Navigation.findNavController(requireView()).navigate(R.id.action_navigation_akun_to_loginFragment)
            }
            dialog.setNegativeButton("Batal") { listener, _ ->
                listener.dismiss()
            }
            dialog.show()
        }
    }

    private fun setUpAccount() {
        viewModel.authGetResponse.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    when(it.data?.code()){
                        200 ->{

                            if (it.data.body()?.imageUrl == null){
                                Glide.with(this)
                                    .load(R.drawable.ic_select_photo)
                                    .placeholder(R.color.black)
                                    .into(binding.ivUserPhoto)
                            }
                            else{
                                Glide.with(requireContext())
                                    .load(it.data.body()?.imageUrl)
                                    .placeholder(R.color.black)
                                    .into(binding.ivUserPhoto)
                            }

                            binding.tvNamaUser.text = it.data.body()?.fullName
                            binding.tvNoWhatsapp.text = it.data.body()?.phoneNumber
                            binding.tvLocation.text = it.data.body()?.city
                        }
                    }
                }

                Status.ERROR ->{
                    val error = it.message
                    Toast.makeText(requireContext(), "Error get Data : $error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}