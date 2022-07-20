package com.binar.secondhand.ui.bagian_product.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.binar.secondhand.R
import com.binar.secondhand.data.resource.Status
import com.binar.secondhand.databinding.FragmentDetailBinding
import com.binar.secondhand.helper.Notif
import com.binar.secondhand.helper.Util
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private var enable = true
    private val viewModel by viewModel<DetailViewModel>()
    private val args: DetailFragmentArgs by navArgs()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val util get() = Util()
    private val notif get() = Notif()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.viewPager.setImageResource(R.drawable.photo_one)

        binding.profileUser.setOnClickListener {
            enable = true
            showButton(enable)
        }

        showButton(enable)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDetailProduct(args.itemId)
        viewModel.getBuyerOrder()

        setUpObserver()

        binding.nego.setOnClickListener {
            when {
                binding.nego.isEnabled -> {
                    enable = false
                    binding.nego.isEnabled = false
                    binding.nego.text = "Menunggu respon penjual"
                }
            }
        }
    }

    private fun setUpObserver() {
        viewModel.detailProduct.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    val harga = it.data?.body()?.basePrice!!
                    Glide.with(binding.viewPager)
                        .load(it.data.body()?.imageUrl)
                        .placeholder(R.color.black)
                        .transform(CenterCrop(), RoundedCorners(12))
                        .into(binding.viewPager)
                    Glide.with(binding.profileUser)
                        .load(it.data.body()?.user?.imageUrl)
                        .placeholder(R.color.black)
                        .transform(CenterCrop(), RoundedCorners(12))
                        .into(binding.profileUser)
                    binding.produk.text = it.data.body()?.name
                    binding.harga.text = util.rupiah(harga)
                    binding.penjelasan.text = it.data.body()?.description.toString()
                    binding.asal.text = it.data.body()?.location
                    binding.namaPenjual.text = it.data.body()?.user?.fullName
                    binding.kategori.text = it.data.body()?.categories?.joinToString {
                        it.name
                    }
                }
                Status.ERROR -> {
                    val error = it.message
                    notif.showToast("Error get Data : $error", requireContext())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showButton(isShow: Boolean) {
        binding.nego.isEnabled = isShow
        binding.nego.text = "Saya tertarik dan ingin nego"
    }
}