package com.binar.secondhand.ui.akun.edit_akun

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.binar.secondhand.R
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentUbahAkunBinding
import com.binar.secondhand.helper.Util
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class UbahAkunFragment : Fragment() {

    private var _binding: FragmentUbahAkunBinding? = null
    private val binding get() = _binding!!
    private val util get() = Util(requireContext())
    private val viewModel: UbahAkunViewModel by viewModel()
    private var imageUri : Uri? = null

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data
                    imageUri = fileUri
                    loadImage(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUbahAkunBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val kota = resources.getStringArray(R.array.city)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, kota)
        binding.autoCompleteTv.setAdapter(arrayAdapter)

        viewModel.getAuth()

        profile()

        binding.ivUser.setOnClickListener {
            openImagePicker()
        }

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
        binding.update.setOnClickListener {
            binding.apply {
                val name = etName.editText?.text.toString()
                val city = edKota.editText?.text.toString()
                val address = etAddress.editText?.text.toString()
                val phoneNumber = etPhone.editText?.text.toString()

                val imageFile = if (imageUri == null){
                    null
                }else{
                    util.uriToFile(imageUri!!)
                }

                val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
                val addressBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
                val phoneNumberBody = phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())

                val requestImage = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageBody = requestImage?.let { it1 ->
                    MultipartBody.Part.createFormData("image",
                        imageFile.name, it1
                    )
                }

                viewModel.putAuth(
                    fullname = nameBody,
                    city = cityBody,
                    address = addressBody,
                    phone_number = phoneNumberBody,
                    image = imageBody
                )
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
                            Snackbar.make(binding.snackbar, "Sukses mengedit akun", Snackbar.LENGTH_LONG)
                                .setAction("x") {
                                    // Responds to click on the action
                                }
                                .setBackgroundTint(resources.getColor(R.color.green))
                                .setActionTextColor(resources.getColor(R.color.white))
                                .show()
                        }
                        403 ->{
                            Snackbar.make(binding.snackbar, "Error code : 403", Snackbar.LENGTH_LONG)
                                .setAction("x") {
                                    // Responds to click on the action
                                }
                                .setBackgroundTint(resources.getColor(R.color.green))
                                .setActionTextColor(resources.getColor(R.color.white))
                                .show()
                        }
                    }
                }

                Status.ERROR ->{
                    val error = it.message
                    Snackbar.make(binding.snackbar, "Error get Data : $error", Snackbar.LENGTH_LONG)
                        .setAction("x") {
                            // Responds to click on the action
                        }
                        .setBackgroundTint(resources.getColor(R.color.green))
                        .setActionTextColor(resources.getColor(R.color.white))
                        .show()
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
                            binding.etName.editText?.setText(it.data.body()?.fullName)
                            binding.edKota.editText?.setText(it.data.body()?.city)
                            binding.etAddress.editText?.setText(it.data.body()?.address)
                            binding.etPhone.editText?.setText(it.data.body()?.phoneNumber.toString())
                        }
                    }
                }

                Status.ERROR ->{
                    val error = it.message
                    Snackbar.make(binding.snackbar, "Error get Data : $error", Snackbar.LENGTH_LONG)
                        .setAction("x") {
                            // Responds to click on the action
                        }
                        .setBackgroundTint(resources.getColor(R.color.green))
                        .setActionTextColor(resources.getColor(R.color.white))
                        .show()
                }
            }
        }
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop(1f, 1f)
            .saveDir(
                File(
                    requireContext().externalCacheDir,
                    "ImagePicker"
                )
            )
            .compress(1024)
            .maxResultSize(
                1080,
                1080
            )
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private fun loadImage(uri: Uri?) {
        uri?.let {
            Glide.with(this)
                .load(it)
                .into(binding.ivUser)
        }
    }

}