package com.binar.secondhand.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.helper.Sharedpref
import com.binar.secondhand.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.data.api.model.seller.banner.get.GetBannerResponse
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var isLogin = false
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HomeViewModel>()
    private val sharedPref get() = Sharedpref(requireContext())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = getKoin().getProperty("access_token", "")
        isLogin = token != ""
        sharedPref.putBooleanKey("login", isLogin)

        val status = sharedPref.getBooleanKey("login")
        if (!status){
            Navigation.findNavController(binding.root).navigate(R.id.action_navigation_home_to_loginFragment)
        }

        setUpObserver()

        viewModel.getHomeBanner()
        viewModel.getHomeCategory()

        binding.tabHomeCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //do filter product here
                if (tab?.id == -1){
                    viewModel.getHomeProduct()
                }else{
                    viewModel.getHomeProduct(categoryId = tab?.id)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpObserver() {
        viewModel.getBannerResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                }

                Status.SUCCESS -> {

                    when (it.data?.code()) {
                        200 -> {
                            val data = it.data.body()
                            showHomeBanner(data)
                        }

                        else ->{
                            viewModel.snackbar("Error occured: ${it.data?.code()}", binding.root)
                        }
                    }
                }

                Status.ERROR -> {
                    viewModel.snackbar("${it.message}", binding.root)
                }
            }
        }

        viewModel.getCategoryResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                }

                Status.SUCCESS -> {

                    when (it.data?.code()) {
                        200 -> {

                            binding.tabHomeCategory.addTab(
                                binding.tabHomeCategory.newTab()
                                    .setText("Semua")
                                    .setIcon(R.drawable.ic_fi_search)
                                    .setId(-1)
                            )

                            val data = it.data.body()
                            if (data != null) {
                                for (category in data) {
                                    binding.tabHomeCategory.addTab(
                                        binding.tabHomeCategory.newTab()
                                            .setText(category.name)
                                            .setIcon(R.drawable.ic_fi_search)
                                            .setId(category.id)
                                    )

                                }
                            }
                        }

                        else ->{
                            viewModel.snackbar("Error occured: ${it.data?.code()}", binding.root)
                        }
                    }
                }

                Status.ERROR -> {
                    viewModel.snackbar("${it.message}", binding.root)
                }
            }
        }

        viewModel.getHomeProductResponse.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {

                }

                Status.SUCCESS -> {

                    when (it.data?.code()) {
                        200 -> {

                            val data = it.data.body()
                            showHomeProductList(data)

                        }
                        else ->{
                            viewModel.snackbar("Error occured: ${it.data?.code()}", binding.root)
                        }
                    }
                }

                Status.ERROR -> {
                    viewModel.snackbar("${it.message}", binding.root)
                }
            }
        }
    }

    private fun showHomeBanner(data: GetBannerResponse?) {
        if (data?.size != 0){
            val adapter = BannerAdapter()
            adapter.submitList(data)
            binding.vpHomeBanner.adapter = adapter
        }
    }

    private fun showHomeProductList(productResponse: GetProductResponse?) {
        val adapter = ItemAdapter {
            val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(it.id)
            findNavController().navigate(action)
        }

        adapter.submitList(productResponse)

        binding.rvHomeProduct.adapter = adapter

    }
}
