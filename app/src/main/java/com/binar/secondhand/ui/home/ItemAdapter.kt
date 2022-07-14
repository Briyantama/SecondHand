package com.binar.secondhand.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.data.api.model.buyer.product.GetProductResponseItem
import com.binar.secondhand.databinding.ItemHomeBinding
import com.binar.secondhand.helper.Util
import com.bumptech.glide.Glide

class ItemAdapter(private val onClick: (GetProductResponseItem) -> Unit):
    ListAdapter<GetProductResponseItem, ItemAdapter.ViewHolder>(CommunityComparator()) {

    inner class ViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val util = Util(binding.root.context)

        fun bind(
            currentGetProductResponseItem: GetProductResponseItem,
            onClick: (GetProductResponseItem) -> Unit
        ) {
            binding.root.setOnClickListener {
                onClick(currentGetProductResponseItem)
            }
            Glide.with(binding.root).load(currentGetProductResponseItem.imageUrl)
                .placeholder(R.color.black)
                .into(binding.ivProduct)
            binding.tvProductTitle.text = currentGetProductResponseItem.name
            binding.tvCategory.text = currentGetProductResponseItem.categories?.joinToString{
                it.name
            }
            binding.tvPrice.text = util.rupiah(currentGetProductResponseItem.basePrice)
        }

    }

    class CommunityComparator : DiffUtil.ItemCallback<GetProductResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GetProductResponseItem,
            newItem: GetProductResponseItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: GetProductResponseItem,
            newItem: GetProductResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

}