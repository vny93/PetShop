package vn.vunganyen.petshop.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.databinding.ItemBrandBinding
import vn.vunganyen.petshop.screens.client.home.seeAllProduct.SeeAllActivity


class AdapterBrand : RecyclerView.Adapter<AdapterBrand.MainViewHolder>() {
    private var listData: List<BrandDetailRes> = ArrayList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<BrandDetailRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemBrandBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bindItem(data: BrandDetailRes) {
            val strUrl: List<String> = data.logo.split("3000/")
            val url = PathApi.BASE_URL + strUrl.get(1)
            Picasso.get().load(url).into(binding.imvBrand)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemBrandBinding.inflate(
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
            var intent = Intent(holder.itemView.context, SeeAllActivity::class.java)
            intent.putExtra("id",data.mahang)
            intent.putExtra("name",data.tenhang)
            holder.itemView.context.startActivity(intent)
        }

    }
}