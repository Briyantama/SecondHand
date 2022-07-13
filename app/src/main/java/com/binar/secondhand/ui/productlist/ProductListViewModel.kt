package com.binar.secondhand.ui.productlist
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import com.binar.secondhand.data.repositories.AuthRepository
//import com.binar.secondhand.data.repositories.SellerRepository
//import com.binar.secondhand.data.source.remote.network.Resource
//import com.binar.secondhand.data.source.remote.response.GetProfileResponse
//
//
//class ProductListViewModel(
//    private val sellerRepository: SellerRepository,
//    private val authRepository: AuthRepository) : ViewModel() {
//
//    val gelAllProduct = sellerRepository.getListProduct()
//    fun getDetailUser(id: Int)  = authRepository.getDetailUser(id)
//
//}