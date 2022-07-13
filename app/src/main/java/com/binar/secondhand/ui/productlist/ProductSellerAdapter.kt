package com.binar.secondhand.ui.productlist
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.AsyncListDiffer
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.binar.secondhand.data.source.remote.response.ProductItem
//import com.binar.secondhand.databinding.ItemListProductStateBinding
//import com.binar.secondhand.utils.CurrencyIndonesia
//import com.binar.secondhand.utils.gone
//import com.binar.secondhand.utils.loadImage
//import com.binar.secondhand.utils.visible
//
//
//class ProductSellerAdapter: RecyclerView.Adapter<ProductSellerAdapter.RecentAdapterViewHolder>() {
//    inner class RecentAdapterViewHolder(val view: ItemListProductStateBinding) :
//        RecyclerView.ViewHolder(view.root)
//
//    private val diffCallback = object : DiffUtil.ItemCallback<ProductItem>() {
//        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
//            return oldItem.hashCode() == newItem.hashCode()
//        }
//    }
//
//    private val differ = AsyncListDiffer(this, diffCallback)
//
//    fun submitData(list: MutableList<ProductItem>) {
//        differ.submitList(list) }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentAdapterViewHolder {
//        val binding = ItemListProductStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return RecentAdapterViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RecentAdapterViewHolder, position: Int) {
//        holder.view.apply {
//            val data = differ.currentList[position]
//
//            if (data.id != 0){
//                itemProduct.root.visible()
//                itemEmpty.root.gone()
//                itemProduct.imgProduct.loadImage(data.image_url)
//                itemProduct.txtName.text = data.name
//                itemProduct.txtCategory.text = data.Categories[0].name
//                itemProduct.txtPrice.text = CurrencyIndonesia.rp(data.base_price.toString())
//            }else {
//                itemProduct.root.gone()
//                itemEmpty.root.visible()
//            }
//
//        }
//    }
//
//    override fun getItemCount(): Int = differ.currentList.size
//
//}