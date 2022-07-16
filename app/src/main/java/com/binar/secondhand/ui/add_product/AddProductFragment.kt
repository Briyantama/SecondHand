package com.binar.secondhand.ui.add_product

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.binar.secondhand.databinding.FragmentAddProductBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat

class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddProductViewModel by viewModel()
    private var imageUri : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setUpObserve()

        binding.edHargaProduk.addTextChangedListener(object : TextWatcher {
            private var current = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != current) {
                    binding.edHargaProduk.removeTextChangedListener(this);

                    val replaceable = String.format("[Rps,.\\s]",
                        NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                    val cleanString = s.toString().replace(replaceable, "");

                    var parsed = try {
                        cleanString.toDouble()
                    } catch (e: NumberFormatException) {
                        0.00
                    }
                    val formatter = NumberFormat.getCurrencyInstance()
                    formatter.maximumFractionDigits = 0
                    val formatted = formatter.format((parsed))

                    current = formatted
                    binding.edHargaProduk.setText(formatted)
                    binding.edHargaProduk.setSelection(formatted.length)
                    binding.edHargaProduk.addTextChangedListener(this)
                }
            }
        })
//        val kota = resources.getStringArray(R.array.city)
//        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, kota)
//        binding.edKotaProduk.setAdapter(arrayAdapter)
//
//        binding.ivPhoto.setOnClickListener {
//            viewModel.imagePicker(requireParentFragment(), requireContext(), binding.ivPhoto, requireView())
//        }
//
//        binding.btnPreview.setOnClickListener {
//            if (binding.edNamaProduk.text.toString().isEmpty() ||
//                binding.edHargaProduk.text.toString().isEmpty() ||
//                binding.edKotaProduk.text.toString().isEmpty() ||
//                binding.edKategoriProduk.text.toString().isEmpty() ||
//                binding.edDeskripsi.text.toString().isEmpty()
//            ) {
//                viewModel.toast("Lengkapi data terlebih dahulu", requireContext())
//            } else {
//                val actionToPreviewFragment =
//                    MainFragmentDirections.actionMainFragmentToPreviewFragment(
//                        name = binding.etName.editText?.text.toString(),
//                        price = binding.etPrice.editText?.text.toString().replace("Rp. ", "")
//                            .replace(",", ""),
//                        location = binding.etCity.editText?.text.toString(),
//                        description = binding.etDescription.editText?.text.toString(),
//                        image = imageUri.toString(),
//                        category = binding.etCategory.editText?.text.toString()
//                    )
//                Navigation.findNavController(requireView()).navigate(actionToPreviewFragment)
//            }
//        }
//
//        binding.btnTerbit.setOnClickListener {
//            if (binding.edNamaProduk.text.toString().isEmpty() ||
//                binding.edHargaProduk.text.toString().isEmpty() ||
//                binding.edKotaProduk.text.toString().isEmpty() ||
//                binding.edKategoriProduk.text.toString().isEmpty() ||
//                binding.edDeskripsi.text.toString().isEmpty()
//            ) {
//                viewModel.toast("Lengkapi data terlebih dahulu", requireContext())
//            } else {
//            binding.apply {
//                val name = edNamaProduk.text.toString()
//                val price = edHargaProduk.text.toString().replace("Rp. ", "").replace(",", "")
//                val city = edKotaProduk.text.toString()
//                val category = edKategoriProduk.text.toString()
//                val description = edDeskripsi.text.toString()
//
//                val imageFile =
//                    if (imageUri == null){ null }
//                    else{ viewModel.uriToFile(imageUri!!, requireContext()) }
//
//                val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
//                val priceBody = price.toRequestBody("text/plain".toMediaTypeOrNull())
//                val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
//                val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())
//                val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
//                val requestImage = imageFile?.let { viewModel.reduceImage(it).asRequestBody("image/jpg".toMediaTypeOrNull()) }
//                val imageBody = requestImage?.let { MultipartBody.Part.createFormData("image", imageFile.name, it) }
//
//                viewModel.postProduct(
//                    name = nameBody,
//                    base_price = priceBody,
//                    location = cityBody,
//                    category_ids = categoryBody,
//                    description = descriptionBody,
//                    image = imageBody
//                )
//            }
//            }
//        }
    }

//    private fun setUpObserve() {
//        viewModel.sellerPostProduct.observe(viewLifecycleOwner) {
//            when (it.status) {
//                Status.LOADING -> {
//                }
//                Status.SUCCESS -> {
//                    when (it.data?.code()) {
//                        201 -> {
//                        }
//                        503 -> {
//                            Snackbar.make(
//                                binding.snackbar,
//                                "Server sedang mengalami gangguan, harap coba lagi nanti.",
//                                Snackbar.LENGTH_LONG
//                            )
//                                .setAction("x") {
//                                    // Responds to click on the action
//                                }
//                                .setBackgroundTint(
//                                    ContextCompat.getColor(
//                                        requireContext(),
//                                        R.color.red
//                                    )
//                                )
//                                .setActionTextColor(
//                                    ContextCompat.getColor(
//                                        requireContext(),
//                                        R.color.white
//                                    )
//                                )
//                                .show()
//                        }
//                        else -> {
//                            Snackbar.make(
//                                binding.snackbar,
//                                "Terjadi kesalahan",
//                                Snackbar.LENGTH_LONG
//                            )
//                                .setAction("x") {
//                                    // Responds to click on the action
//                                }
//                                .setBackgroundTint(
//                                    ContextCompat.getColor(
//                                        requireContext(),
//                                        R.color.red
//                                    )
//                                )
//                                .setActionTextColor(
//                                    ContextCompat.getColor(
//                                        requireContext(),
//                                        R.color.white
//                                    )
//                                )
//                                .show()
//                        }
//                    }
//                }
//                Status.ERROR -> {
//                    Toast.makeText(context, "Gagal terbit", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
