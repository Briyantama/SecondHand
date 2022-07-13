package com.binar.secondhand.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.data.api.model.seller.banner.get.GetBannerResponseItem
import com.binar.secondhand.databinding.BannerBinding
import com.binar.secondhand.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide

class BannerAdapter(private val onClick: (GetBannerResponseItem) -> Unit) :
    ListAdapter<GetBannerResponseItem, BannerAdapter.ViewHolder>(CommunityComparator()) {


    class ViewHolder(private val binding: BannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currentBanner: GetBannerResponseItem,
            onClick: (GetBannerResponseItem) -> Unit
        ) {
            binding.root.setOnClickListener {
                onClick(currentBanner)
            }
            Glide.with(binding.root).load(currentBanner.imageUrl).into(binding.root)

        }

    }

    class CommunityComparator : DiffUtil.ItemCallback<GetBannerResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GetBannerResponseItem,
            newItem: GetBannerResponseItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: GetBannerResponseItem,
            newItem: GetBannerResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

}