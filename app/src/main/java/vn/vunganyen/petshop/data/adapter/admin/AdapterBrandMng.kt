package vn.vunganyen.petshop.data.adapter.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.databinding.ItemBrand2Binding
import vn.vunganyen.petshop.screens.admin.inputData.mngBrand.updateBrand.BrandDetailMngActivity


class AdapterBrandMng : RecyclerView.Adapter<AdapterBrandMng.MainViewHolder>() {
    private var listData: List<BrandDetailRes> = ArrayList()
    var click: ((data : BrandDetailRes)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<BrandDetailRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemBrand2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: BrandDetailRes) {
            if(!(data.logo.isEmpty())){
                val strUrl: List<String> = data.logo.split("3000/")
                val url = PathApi.BASE_URL + strUrl.get(1)
                Picasso.get().load(url).into(binding.imvBrandMng)
            }
            binding.tvIdBrand.text = data.mahang
            binding.tvNameBrand.text = data.tenhang
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemBrand2Binding.inflate(
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
            var intent = Intent(holder.itemView.context, BrandDetailMngActivity::class.java)
            intent.putExtra("data",data)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.deleteBrand.setOnClickListener{
            click?.invoke(data)
        }

    }
}