package com.binar.secondhand.ui.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.binar.secondhand.databinding.FragmentProductListBinding

class ProductListFragment : Fragment(){

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//
//    private var _binding: FragmentProductListBinding? = null
//    private val binding get() = _binding!!
//    private val viewModel by viewModel<ProductListViewModel>()
//    private val productAdapter by lazy { ProductSellerAdapter() }
//    private var productList: ArrayList<ProductItem> = arrayListOf()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        _binding = FragmentProductListBinding.inflate(layoutInflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.apply {
//            actionBar.txtTitle.text = getString(R.string.daftar_jual_saya)
//
//            productList.add(ProductItem(id=0))
//            rvProduct.apply {
//                adapter = productAdapter
//                layoutManager = GridLayoutManager(context, 2)
//                val dividerItemDecoration =
//                    DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
//                ContextCompat.getDrawable(context, R.drawable.divider)?.let {
//                    dividerItemDecoration.setDrawable(it)
//                }
//                if (this.itemDecorationCount == 0)
//                    addItemDecoration(dividerItemDecoration)
//            }
//
//        }
//
//        observeData()
//        observeDetailUser()
//    }
//
//    private fun observeDetailUser() {
//        viewModel.getDetailUser(65).observe(viewLifecycleOwner) { response ->
//            when (response) {
//                is Resource.Loading -> Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
//                is Resource.Success -> {
//                    binding.apply {
//                        txtName.text = response.data?.full_name
//                        txtKota.text = response.data?.address
//                        imgProfile.loadImage(response.data?.image_url)
//                    }
//                }
//                is Resource.Error -> {
//                    Toast.makeText(context, "error ${response.message} ", Toast.LENGTH_SHORT).show()
//                    Log.d("err", "error ${response.message}")
//                }
//            }
//            Log.d("activity_main", "${response.data} ")
//        }
//    }
//
//    private fun observeData() {
//        viewModel.gelAllProduct.observe(viewLifecycleOwner) { response ->
//            when (response) {
//                is Resource.Loading -> Toast.makeText(context, "loading", Toast.LENGTH_SHORT).show()
//                is Resource.Success -> {
//                    response.data?.let { productList.addAll(it) }
//                    productAdapter.submitData(productList)
//                }
//                is Resource.Error -> {
//                    Toast.makeText(context, "error ${response.message} ", Toast.LENGTH_SHORT).show()
//                    Log.d("err", "error ${response.message}")
//                }
//            }
//            Log.d("activity_main", "${response.data} ")
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//
//}