package com.binar.secondhand.ui.bagian_product.add_product.sheet_kategori

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.data.api.model.seller.category.get.GetCategoryResponseItem
import com.binar.secondhand.databinding.SellCategoryItemBinding
import com.binar.secondhand.helper.listCategory

class BottomSheetCategoryAdapter(
    private val selected: (GetCategoryResponseItem)->Unit,
    private val unselected: (GetCategoryResponseItem)->Unit
) : RecyclerView.Adapter<BottomSheetCategoryAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<GetCategoryResponseItem>(){

        override fun areItemsTheSame(
            oldItem: GetCategoryResponseItem,
            newItem: GetCategoryResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetCategoryResponseItem,
            newItem: GetCategoryResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<GetCategoryResponseItem>?) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(SellCategoryItemBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: SellCategoryItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(data: GetCategoryResponseItem){
            binding.apply {
                cbCategory.text = data.name
                cbCategory.isChecked = listCategory.contains(data.name)
                cbCategory.setOnClickListener{
                    if (!listCategory.contains(data.name)){
                        selected.invoke(data)
                    } else {
                        unselected.invoke(data)
                    }
                }
            }
        }
    }
}
