package com.binar.secondhand.ui.bagian_akun.change_pass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentChangePassBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePassFragment : Fragment() {

    private var show1 = false
    private var show2 = false
    private var show3 = false
    private var _binding: FragmentChangePassBinding? = null
    private val binding get() = _binding!!
    private val viewModel : ChangePassViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePassBinding.inflate(inflater, container, false)

        binding.passwordLamaToggle.setOnClickListener {
            show1 = !show1
            viewModel.show(binding.edPasswordLama, binding.passwordLamaToggle, show1)
        }
        binding.passwordBaruToggle.setOnClickListener {
            show2 = !show2
            viewModel.show(binding.edPasswordBaru, binding.passwordBaruToggle, show2)
        }
        binding.passwordxBaruToggle.setOnClickListener {
            show3 = !show3
            viewModel.show(binding.edPasswordBaruX, binding.passwordxBaruToggle, show3)
        }

        viewModel.show(binding.edPasswordLama, binding.passwordLamaToggle, show1)
        viewModel.show(binding.edPasswordBaru, binding.passwordBaruToggle, show2)
        viewModel.show(binding.edPasswordBaruX, binding.passwordxBaruToggle, show3)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        password()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnGanti.setOnClickListener{
            val changePassReq = PutPassRequest(
                binding.edPasswordLama.text.toString(),
                binding.edPasswordBaru.text.toString(),
                binding.edPasswordBaruX.text.toString()
            )
            if (binding.edPasswordLama.text.isNullOrEmpty() || binding.edPasswordBaru.text.isNullOrEmpty() || binding.edPasswordBaruX.text.isNullOrEmpty()) {
                viewModel.toast("Kolom tidak boleh kosong", requireContext())
            }
            else if (binding.edPasswordBaru.text.toString().length < 6) {
                viewModel.toast("Password minimal 6 character", requireContext())
            }
            else if (binding.edPasswordBaru.text.toString().lowercase() != binding.edPasswordBaruX.text.toString().lowercase()) {
                viewModel.toast("Password tidak sama", requireContext())
            }
            else{
                viewModel.putChangePass(changePassReq)
            }
        }
    }

    private fun password() {
        viewModel.putChangePassResponse.observe(viewLifecycleOwner){
            when (it.status){
                Status.LOADING ->{}
                Status.SUCCESS -> {
                    viewModel.snackbarGreen("Password berhasil diubah", binding.snackbar, resources)
                    Navigation.findNavController(requireView()).navigateUp()
                }
                Status.ERROR -> {
                    viewModel.toast(it.message.toString(), requireContext())
                }
            }
        }
    }

}