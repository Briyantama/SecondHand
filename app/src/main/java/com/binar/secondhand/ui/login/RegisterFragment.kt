package com.binar.secondhand.ui.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private var isShowPass = false
    private var isShowPass2 = false
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<RegisterUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        binding.passwordToggle.setOnClickListener {
            isShowPass =  !isShowPass
            showPassword(isShowPass)
        }

        binding.passwordToggle2.setOnClickListener {
            isShowPass2 =  !isShowPass2
            showPassword2(isShowPass2)
        }

        showPassword(isShowPass)
        showPassword2(isShowPass2)
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
                Toast.makeText(context, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else if (binding.edPassword.text.toString().length < 6) {
                Toast.makeText(context, "Password minimal 6 character", Toast.LENGTH_SHORT).show()
            }
            else if (binding.edPassword.text.toString().lowercase() != binding.edPassword2.text.toString().lowercase()) {
                Toast.makeText(context, "Password tidak sama", Toast.LENGTH_SHORT).show()
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
                            val data = it.data.body()

                            val id = data?.id
                            val fullName = data?.fullName
                            val email = data?.email
                            val password = data?.password
                            val phoneNumber = data?.phoneNumber
                            val address = data?.address
                            val imageUrl = data?.imageUrl
                            val createdAt = data?.createdAt
                            val updatedAt = data?.updatedAt

                            Toast.makeText(context, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }

                        404 -> {
                            Snackbar.make(binding.root, "Email Already Exists", Snackbar.LENGTH_INDEFINITE).show()
                        }

                        500 -> {
                            Snackbar.make(binding.root, "Email Already Exists", Snackbar.LENGTH_INDEFINITE).show()
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

    private fun showPassword2(isShow : Boolean){
        if (isShow){
            binding.edPassword2.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.passwordToggle2.setImageResource(R.drawable.ic_outline_remove_red_eye_24)
        }else{
            binding.edPassword2.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.passwordToggle2.setImageResource(R.drawable.ic_outline_visibility_off_24)
        }
        binding.edPassword2.setSelection(binding.edPassword2.text.toString().length)
    }
}