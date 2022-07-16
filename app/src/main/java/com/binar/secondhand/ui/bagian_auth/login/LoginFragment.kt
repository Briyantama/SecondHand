package com.binar.secondhand.ui.bagian_auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentLoginBinding
import com.binar.secondhand.helper.Sharedpref
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var isShowPass = false
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<LoginUserViewModel>()
    private val sharedPref get() = Sharedpref(requireContext())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        binding.passwordToggle.setOnClickListener {
            isShowPass = !isShowPass
            viewModel.show(binding.edPassword, binding.passwordToggle, isShowPass)
        }

        viewModel.show(binding.edPassword, binding.passwordToggle, isShowPass)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()

        binding.btnLogin.setOnClickListener {
            val loginPostRequest = PostLoginRequest(
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString()
            )

            if (binding.edEmail.text.isNullOrEmpty() || binding.edPassword.text.isNullOrEmpty()) {
                viewModel.toast("Email atau Password tidak boleh kosong", requireContext())
            }
            else {
                viewModel.postLogin(loginPostRequest)
            }
        }

        binding.tvDaftar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setUpObserver() {

        viewModel.loginPostResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        201 -> {
                            val data = it.data.body()

                            val accesToken = data?.accessToken

                            getKoin().setProperty("access_token", accesToken.toString())
                            sharedPref.putBooleanKey("login", true)
                            viewModel.toast("Berhasil Login", requireContext())
                            findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
                        }

                        401 -> {
                            viewModel.snackbarWithAction("Email or Password Are Wrong","Oke", {}, requireView())
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