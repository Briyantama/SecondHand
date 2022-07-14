package com.binar.secondhand.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private var isShowPass = false
    private val viewModel by viewModel<RegisterUserViewModel>()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        binding.passwordToggle.setOnClickListener {
            isShowPass = !isShowPass
            viewModel.show(binding.edPassword, binding.passwordToggle, isShowPass)
        }

        binding.passwordToggle2.setOnClickListener {
            isShowPass = !isShowPass
            viewModel.show(binding.edPassword2, binding.passwordToggle2, isShowPass)
        }

        viewModel.show(binding.edPassword2, binding.passwordToggle2, isShowPass)
        viewModel.show(binding.edPassword, binding.passwordToggle, isShowPass)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()

        binding.btnRegist.setOnClickListener {
            val registerPostRequest = PostRegisterRequest(
                binding.etNamaRegister.text.toString(),
                binding.etEmailRegister.text.toString(),
                binding.edPassword.text.toString(),
                0,"-",R.color.black.toString(),"-"
            )

            if (binding.etNamaRegister.text.isNullOrEmpty() || binding.etEmailRegister.text.isNullOrEmpty() || binding.edPassword.text.isNullOrEmpty()) {
                viewModel.toast("Kolom tidak boleh kosong", requireContext())
            }
            else if (binding.edPassword.text.toString().length < 6) {
                viewModel.toast("Password minimal 6 character", requireContext())
            }
            else if (binding.edPassword.text.toString().lowercase() != binding.edPassword2.text.toString().lowercase()) {
                viewModel.toast("Password tidak sama", requireContext())
            }
            else {
                viewModel.postRegister(registerPostRequest)
            }

        }

        binding.tvMasuk.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun setUpObserver() {

        viewModel.registerPostResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        201 -> {
                            viewModel.toast("Registrasi Berhasil", requireContext())
                            findNavController().navigateUp()
                        }

                        404 -> {
                            viewModel.snackbar("Email Already Exists", binding.root)
                        }

                        500 -> {
                            viewModel.snackbar("Internal Service Error", binding.root)
                        }
                    }
                }

                Status.ERROR -> {
                    val error = it.message
                    viewModel.toast(error.toString(), requireContext())
                }
            }
        }
    }
}