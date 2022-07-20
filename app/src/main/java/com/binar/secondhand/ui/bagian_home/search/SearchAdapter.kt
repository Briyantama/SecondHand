package com.binar.secondhand.ui.bagian_home.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.data.api.model.buyer.product.GetProductResponseItem
import com.binar.secondhand.databinding.SearchItemBinding
import com.binar.secondhand.helper.Util
import com.bumptech.glide.Glide

class SearchAdapter(private val onClick: (GetProductResponseItem) -> Unit) :
    ListAdapter<GetProductResponseItem, SearchAdapter.ViewHolder>(CommunityComparator()) {

    class ViewHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal val util get() = Util()

        fun bind(
            currentGetProductResponseItem: GetProductResponseItem,
            onClick: (GetProductResponseItem) -> Unit
        ) {
            binding.root.setOnClickListener {
                onClick(currentGetProductResponseItem)
            }
            Glide.with(binding.root).load(currentGetProductResponseItem.imageUrl)
                .placeholder(R.color.black)
                .into(binding.imvProductImage)
            binding.tvProductName.text = currentGetProductResponseItem.name
            binding.tvProductCategory.text = currentGetProductResponseItem.categories?.joinToString{
                it.name
            }
            binding.tvProductPrice.text = util.rupiah(currentGetProductResponseItem.basePrice)
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
        val binding = SearchItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

}