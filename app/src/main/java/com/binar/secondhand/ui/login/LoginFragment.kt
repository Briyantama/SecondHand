package com.binar.secondhand.ui.login

import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentLoginBinding
import com.binar.secondhand.helper.Sharedpref
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var isShowPass = false
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<LoginUserViewModel>()
    private lateinit var sharedPref : Sharedpref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        binding.passwordToggle.setOnClickListener {
            isShowPass =  !isShowPass
            showPassword(isShowPass)
        }

        showPassword(isShowPass)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = Sharedpref(requireContext())
        val status = sharedPref.getBooleanKey("login")
        if (status){
            view.findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
        }

        setUpObserver()

        binding.btnLogin.setOnClickListener {
            val loginPostRequest = PostLoginRequest(
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString()
            )

            if (binding.edEmail.text.isNullOrEmpty() || binding.edPassword.text.isNullOrEmpty()) {
                Toast.makeText(context, "Email atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else {
                viewModel.postLogin(loginPostRequest)
            }
        }

        binding.tvDaftar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun showPassword(isShow : Boolean){
        if (isShow){
            binding.edPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.passwordToggle.setImageResource(R.drawable.ic_outline_remove_red_eye_24)
        }else{
            binding.edPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.passwordToggle.setImageResource(R.drawable.ic_outline_visibility_off_24)
        }
        binding.edPassword.setSelection(binding.edPassword.text.toString().length)
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

                            sharedPref = Sharedpref(requireContext())
                            sharedPref.putBooleanKey("login", true)
                            findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
                        }

                        404 -> {
                            val snackbar = Snackbar.make(binding.root, "Email or Password Are Wrong", Snackbar.LENGTH_INDEFINITE)
                            snackbar.setAction("Oke") {}
                            snackbar.show()

                        }

                        500 -> {
                            Snackbar.make(binding.root, "Internal Service Error", Snackbar.LENGTH_INDEFINITE).show()
                        }
                    }
                }

                Status.ERROR -> {

                    val error = it.message
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}