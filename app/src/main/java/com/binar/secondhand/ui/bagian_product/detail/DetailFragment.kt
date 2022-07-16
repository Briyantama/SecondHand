package com.binar.secondhand.ui.bagian_product.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.binar.secondhand.R
import com.binar.secondhand.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var enable = true
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val assets = listOf(
            R.drawable.photo_one,
            R.drawable.photo_two,
            R.drawable.photo_three
        )

        binding.profileUser.setOnClickListener {
            enable =  true
            showButton(enable)
        }

        showButton(enable)
        createSlider(assets)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun createSlider(list: List<Int>) {
        val mAdapter = ImageDetailAdapter(list)

        binding.viewPager.adapter = mAdapter
        binding.viewPager.currentItem = 0
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager(binding.viewPager)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager.unregisterOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {}
        )
    }

    fun showButton(isShow : Boolean){
        binding.nego.isEnabled = isShow
        binding.nego.text = "Saya tertarik dan ingin nego"
    }
}