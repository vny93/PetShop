package vn.vunganyen.petshop.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.client.productType.ProductTypeRes
import vn.vunganyen.petshop.databinding.ItemProductTypeBinding
import vn.vunganyen.petshop.screens.client.product.ProductActivity
import java.util.*

class AdapterProductType : RecyclerView.Adapter<AdapterProductType.MainViewHolder>() {
    private var listData: List<ProductTypeRes> = ArrayList()
    fun setData(list: List<ProductTypeRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemProductTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bindItem(data: ProductTypeRes) {
            if (data.hinhanh != null) {
                val strUrl: List<String> = data.hinhanh.split("3000/")
                var url = PathApi.BASE_URL + strUrl.get(1)
                Picasso.get().load(url).into(binding.imvProductType)
            }
            binding.tvProdcutType.setText(data.tenloaisp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemProductTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = listData[position]
        holder.bindItem(data)
        holder.itemView.setOnClickListener{
            var intent = Intent(holder.itemView.context, ProductActivity::class.java)
            intent.putExtra("data",data)
            holder.itemView.context.startActivity(intent)
        }
    }
}