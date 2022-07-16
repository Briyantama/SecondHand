package com.binar.secondhand.ui.bagian_akun.edit_akun

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.binar.secondhand.R
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentUbahAkunBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class UbahAkunFragment : Fragment() {

    private var _binding: FragmentUbahAkunBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UbahAkunViewModel by viewModel()
    private var imageUri : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUbahAkunBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val kota = resources.getStringArray(R.array.city)
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, kota)
        binding.edKota.setAdapter(adapter)
        binding.edKota.enoughToFilter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAuth()
        profile()

        binding.ivUser.setOnClickListener {
            viewModel.imagePicker(requireParentFragment(), requireContext(), binding.ivUser, requireView())
        }

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.update.setOnClickListener {
            binding.apply {
                val name = edNama.text.toString()
                val city = edKota.text.toString()
                val address = edAlamat.text.toString()
                val phoneNumber = edNomor.text.toString()

                val imageFile =
                    if (imageUri == null){ null }
                    else{ viewModel.uriToFile(imageUri!!, requireContext()) }

                val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
                val addressBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
                val phoneNumberBody = phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())
                val requestImage = imageFile?.let { viewModel.reduceImage(it).asRequestBody("image/jpg".toMediaTypeOrNull()) }
                val imageBody = requestImage?.let { MultipartBody.Part.createFormData("image", imageFile.name, it) }

                viewModel.putAuth(
                    fullname = nameBody,
                    city = cityBody,
                    address = addressBody,
                    phone_number = phoneNumberBody,
                    image = imageBody)
            }
        }

    }

    private fun profile(){

        viewModel.authPutResponse.observe(viewLifecycleOwner){
            when(it.status){

                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    when(it.data?.code()){
                        200 ->{
                            viewModel.snackbarGreen("Sukses mengedit akun", binding.snackbar, resources)
                            Navigation.findNavController(requireView()).navigateUp()
                        }
                        403 ->{
                            viewModel.snackbarGreen("Error code : 403", binding.snackbar, resources)
                        }
                    }
                }

                Status.ERROR ->{
                    val error = it.message.toString()
                    viewModel.snackbarGreen(error, binding.snackbar, resources)
                }
            }
        }

        viewModel.authGetResponse.observe(viewLifecycleOwner){
            when(it.status){

                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    when(it.data?.code()){
                        200 ->{
                            if (it.data.body()?.imageUrl == null){
                                if (imageUri == null){
                                    Glide.with(this)
                                        .load(R.drawable.ic_select_photo)
                                        .placeholder(R.color.black)
                                        .transform(CenterCrop(), RoundedCorners(50))
                                        .into(binding.ivUser)
                                }
                            }else{
                                Glide.with(requireContext())
                                    .load(it.data.body()?.imageUrl)
                                    .placeholder(R.color.black)
                                    .transform(CenterCrop(), RoundedCorners(50))
                                    .into(binding.ivUser)
                            }
                            binding.edNama.setText(it.data.body()?.fullName)
                            binding.edKota.setText(it.data.body()?.city)
                            binding.edAlamat.setText(it.data.body()?.address)
                            binding.edNomor.setText(it.data.body()?.phoneNumber.toString())
                        }
                    }
                }

                Status.ERROR ->{
                    val error = it.message.toString()
                    viewModel.snackbarGreen(error, binding.snackbar, resources)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}