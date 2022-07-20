package com.binar.secondhand.ui.bagian_product.add_product

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.binar.secondhand.R
import com.binar.secondhand.data.local.room.model.DataPreview
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentAddProductBinding
import com.binar.secondhand.helper.*
import com.binar.secondhand.ui.bagian_product.add_product.sheet_kategori.SheetCategory
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddProductViewModel by viewModel()
    private var imageUri: Uri? = null
    private val util get() = Util()
    private val notif get() = Notif()
    private val image get() = ImageFile()
    private val productImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult -> image.startImageResult(
            requireView(),
            binding.ivPhoto,
            requireContext(),
            result) { uri ->
                    imageUri = uri
        }}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)

        binding.edHargaProduk.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().startsWith("Rp. ")) {
                    util.setMaskingMoney("Rp. ", binding.edHargaProduk)
                    Selection.setSelection(binding.edHargaProduk.text,
                        binding.edHargaProduk.text.toString().length)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserve()
        setUpEditText()

        binding.ivPhoto.setOnClickListener {
            image.openImagePicker(
                requireParentFragment(),
                requireContext()
            ){ intent ->
                productImage.launch(intent)
            }
        }

        viewModel.categoryList.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                var kategori = ""
                for (element in it){
                    kategori += ", $element"
                }
                binding.etKategoriProduk.editText!!.setText(kategori.drop(2))
            }
        }

        binding.edKategoriProduk.setOnClickListener {
            val bottomFragment = SheetCategory(
                submit = {viewModel.addCategory(listCategory)}
            )
            bottomFragment.show(parentFragmentManager,"Category")
        }

        val kota = resources.getStringArray(R.array.city)
        val dataKota = ArrayAdapter(requireContext(), R.layout.dropdown_item, kota)
        binding.edKotaProduk.setAdapter(dataKota)

        binding.btnBack.setOnClickListener {
            listCategory.clear()
            listCategoryId.clear()
            Navigation.findNavController(requireView()).navigateUp()
        }

        binding.btnPreview.setOnClickListener {
            if (binding.edNamaProduk.text.toString().isEmpty() ||
                binding.edHargaProduk.text.toString().isEmpty() ||
                binding.edKotaProduk.text.toString().isEmpty() ||
                binding.edKategoriProduk.text.toString().isEmpty() ||
                binding.edDeskripsi.text.toString().isEmpty()
            ) {
                notif.showToast("Lengkapi data terlebih dahulu", requireContext())
            } else {
                val action = AddProductFragmentDirections
                    .actionNavigationJualToPreviewFragment(DataPreview(
                        binding.edNamaProduk.text.toString(),
                        binding.edHargaProduk.text.toString(),
                        binding.edKotaProduk.text.toString(),
                        binding.edDeskripsi.text.toString(),
                        imageUri!!,
                        binding.edKategoriProduk.text.toString(),
                        listCategoryId
                    ))
                Navigation.findNavController(requireView()).navigate(action)
            }
        }

        binding.btnTerbit.setOnClickListener {
            if (binding.edNamaProduk.text.toString().isEmpty() ||
                binding.edHargaProduk.text.toString().isEmpty() ||
                binding.edKotaProduk.text.toString().isEmpty() ||
                binding.edKategoriProduk.text.toString().isEmpty() ||
                binding.edDeskripsi.text.toString().isEmpty()
            ) {
                notif.showToast("Lengkapi data terlebih dahulu", requireContext())
            } else {
                binding.apply {
                    val name = edNamaProduk.text.toString()
                    val price = edHargaProduk.text.toString().replace("Rp. ", "").replace(",", "")
                    val city = edKotaProduk.text.toString()
                    val description = edDeskripsi.text.toString()

                    val imageFile =
                        if ( imageUri == null ) { null }
                        else { image.uriToFile(imageUri!!, requireContext()) }

                    val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
                    val priceBody = price.toRequestBody("text/plain".toMediaTypeOrNull())
                    val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
                    val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
                    val requestImage = imageFile?.let { image.reduceImageSize(it)
                        .asRequestBody("image/jpg".toMediaTypeOrNull()) }
                    val imageBody = requestImage?.let { MultipartBody.Part
                        .createFormData("image", imageFile.name, it ) }

                    viewModel.postProduct(
                        name = nameBody,
                        base_price = priceBody,
                        location = cityBody,
                        category_ids = listCategoryId,
                        description = descriptionBody,
                        image = imageBody
                    )
                }
            }
        }
    }

    private fun setUpObserve() {
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
                            Navigation.findNavController(requireView()).navigate(R.id.action_navigation_jual_to_navigation_list)
                        }
                        503 -> {
                            notif.showSnackbarRed(
                                "Server sedang mengalami gangguan, harap coba lagi nanti.",
                                binding.snackbar,
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
                                binding.snackbar,
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

    private fun setUpEditText() {
        binding.edNamaProduk.doAfterTextChanged { binding.etNamaProduk.hint( "Nama Produk" ) }
        binding.edHargaProduk.doAfterTextChanged { binding.etHargaProduk.hint( "Harga Produk" ) }
        binding.edKotaProduk.doAfterTextChanged { binding.etKotaProduk.hint( "Kota" ) }
        binding.edDeskripsi.doAfterTextChanged { binding.etDeskripsiProduk.hint( "Deskripsi" ) }
        binding.edKategoriProduk.doAfterTextChanged { binding.etKategoriProduk.hint( "Kategori" ) }
    }

    private fun TextInputLayout.hint( string: String ) {
        this.hint = string
    }
}