package com.binar.secondhand.ui.bagian_home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentSearchBinding
import com.binar.secondhand.helper.Notif
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val notif get() = Notif()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.getProduct(searchKeyword = text.toString())
            setUpObserver(text.toString())
        }
        
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun setUpObserver(s : String) {
        viewModel.productResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        200 -> {
                            val data = it.data.body()
                            showProductList(data, s)
                        }

                        else -> {
                            notif.showSnackbar("Error occured: ${it.data?.code()}", requireView())
                        }
                    }
                }
                Status.ERROR -> {
                    notif.showSnackbar("Error occured: ${it.data?.code()}", requireView())
                }
            }
        }
    }

    private fun showProductList(productResponse: GetProductResponse?, s: String) {
        binding.tvSearchResult.text = "Hasil pencarian untuk $s," +
                " ${productResponse?.size ?: 0} ditemukan"
        val adapter = SearchAdapter {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(it.id)
            findNavController().navigate(action)
        }

        adapter.submitList(productResponse)

        binding.rvHomeProduct.adapter = adapter
    }

}