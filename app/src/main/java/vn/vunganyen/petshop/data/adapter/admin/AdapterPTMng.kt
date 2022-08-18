package vn.vunganyen.petshop.data.adapter.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.client.productType.ProductTypeRes
import vn.vunganyen.petshop.databinding.ItemManageBinding
import vn.vunganyen.petshop.screens.admin.inputData.mngProductType.custom.CustomPTMngActivity


class AdapterPTMng : RecyclerView.Adapter<AdapterPTMng.MainViewHolder>() {
    private var listData: List<ProductTypeRes> = ArrayList()
    var click: ((data : ProductTypeRes)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ProductTypeRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemManageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: ProductTypeRes) {
            if(data.hinhanh != null){
                val strUrl: List<String> = data.hinhanh.split("3000/")
                val url = PathApi.BASE_URL + strUrl.get(1)
                Picasso.get().load(url).into(binding.imvMng)
            }
            binding.tvIdMng.text = data.maloaisp
            binding.tvNameMng.text = data.tenloaisp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemManageBinding.inflate(
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
            var intent = Intent(holder.itemView.context, CustomPTMngActivity::class.java)
            intent.putExtra("type","update")
            intent.putExtra("data",data)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.deleteMng.setOnClickListener{
            click?.invoke(data)
        }

    }
}