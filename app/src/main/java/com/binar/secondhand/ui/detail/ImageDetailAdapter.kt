package com.binar.secondhand.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.databinding.AdapterSliderBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class ImageDetailAdapter(private val List: List<Int>)
    : RecyclerView.Adapter<ImageDetailAdapter.ImageSlider>() {

    inner class ImageSlider( val binding: AdapterSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        private var background = RequestOptions().placeholder(R.color.black)

        fun masukan(list: Int) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(list)
                    .error(background)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSlider {
        val binding = AdapterSliderBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ImageSlider(binding)
    }

    override fun onBindViewHolder(holder: ImageSlider, position: Int) {
        holder.masukan(List[position])
    }

    override fun getItemCount(): Int = List.size
}