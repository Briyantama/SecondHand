package com.binar.secondhand.ui.bagian_product.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.R
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentPreviewBinding
import com.binar.secondhand.helper.ImageFile
import com.binar.secondhand.helper.Notif
import com.binar.secondhand.helper.listCategory
import com.binar.secondhand.helper.listCategoryId
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class PreviewFragment : Fragment() {

    private val viewModel: PreviewViewModel by viewModel()
    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!
    private val notif get() = Notif()
    private val image get() = ImageFile()
    private val args:PreviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAuth()
        setUpAkun()


        Glide.with(this)
            .load(args.dataPreview.image)
            .placeholder(R.color.black)
            .transform(CenterCrop(), RoundedCorners(12))
            .into(binding.viewPager)
        binding.penjelasan.text = args.dataPreview.desc
        binding.produk.text = args.dataPreview.name
        binding.kategori.text = args.dataPreview.category
        binding.harga.text = args.dataPreview.price
        binding.asal.text = args.dataPreview.location

        binding.nego.setOnClickListener {
            val name = args.dataPreview.name
            val price = args.dataPreview.price.replace("Rp. ", "").replace(",", "")
            val city = args.dataPreview.location
            val description = args.dataPreview.desc
            val imageFile =
                image.uriToFile(args.dataPreview.image, requireContext())

            val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val priceBody = price.toRequestBody("text/plain".toMediaTypeOrNull())
            val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
            val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
            val requestImage = image.reduceImageSize(imageFile)
                .asRequestBody("image/jpg".toMediaTypeOrNull())
            val imageBody = MultipartBody.Part
                .createFormData("image", imageFile.name, requestImage)
            viewModel.postProduct(
                nameBody,
                descriptionBody,
                priceBody,
                listCategoryId,
                cityBody,
                imageBody
            )
        }
    }

    private fun setUpAkun() {
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
                                    .transform(CenterCrop(), RoundedCorners(12))
                                    .into(binding.profileUser)
                            }
                            else{
                                Glide.with(requireContext())
                                    .load(it.data.body()?.imageUrl)
                                    .placeholder(R.color.black)
                                    .transform(CenterCrop(), RoundedCorners(12))
                                    .into(binding.profileUser)
                            }
                            binding.namaPenjual.text = it.data.body()?.fullName
                        }
                    }
                }

                Status.ERROR ->{
                    val error = it.message.toString()
                    notif.showToast(error, requireContext())
                }
            }
        }

        viewModel.sellerPostProduct.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        201 -> {
                            notif.showToast("Barang Anda berhasil di terbitkan", requireContext())
                            listCategoryId.clear()
                            listCategory.clear()
                            Navigation.findNavController(requireView()).navigate(R.id.action_previewFragment_to_navigation_list)
                        }
                        503 -> {
                            notif.showSnackbarRed(
                                "Server sedang mengalami gangguan, harap coba lagi nanti.",
                                binding.root,
                                resources
                            ){}
                        }
                        400 -> {
                            notif.showSnackbarRed(
                                "Maksimal 5 Barang",
                                binding.root,
                                resources
                            ){}
                        }
                        else -> {
                            notif.showSnackbarRed(
                                "Terjadi kesalahan",
                                binding.root,
                                resources
                            ){}
                        }
                    }
                }
                Status.ERROR -> {
                    notif.showToast("Gagal terbit", requireContext())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}