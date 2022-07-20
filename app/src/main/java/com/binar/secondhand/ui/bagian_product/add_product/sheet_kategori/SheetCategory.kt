package com.binar.secondhand.ui.bagian_product.add_product.sheet_kategori

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentSheetCategoryBinding
import com.binar.secondhand.helper.listCategory
import com.binar.secondhand.helper.listCategoryId
import com.binar.secondhand.ui.bagian_product.add_product.AddProductViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SheetCategory(private val submit: ()-> Unit) : BottomSheetDialogFragment() {

    private val viewModel: AddProductViewModel by viewModel()
    private var _binding: FragmentSheetCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSheetCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnKirimCategory.setOnClickListener {
            viewModel.addCategory(listCategory)
            submit.invoke()
            dismiss()
        }
        viewModel.getCategory()
        viewModel.category.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    if(it.data != null){
                        val adapter = BottomSheetCategoryAdapter(
                            selected = { selected ->
                                listCategory.add(selected.name)
                                listCategoryId.add(selected.id)
                            },
                            unselected = { unselected ->
                                listCategory.remove(unselected.name)
                                listCategoryId.remove(unselected.id)
                            }
                        )
                        adapter.submitData(it.data)
                        binding.rvPilihKategori.adapter = adapter
                    }
                }
                Status.ERROR -> {
                    AlertDialog.Builder(requireContext())
                        .setMessage(it.message)
                        .setPositiveButton("Ok"){ dialog, _ ->
                            dialog.dismiss()
                            findNavController().popBackStack()
                        }
                        .show()
                }
                Status.LOADING -> {
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}