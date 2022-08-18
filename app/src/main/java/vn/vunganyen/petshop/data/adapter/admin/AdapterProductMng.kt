package vn.vunganyen.petshop.data.adapter.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.admin.product.getList.ProductOriginalRes
import vn.vunganyen.petshop.databinding.ItemManageBinding


class AdapterProductMng : RecyclerView.Adapter<AdapterProductMng.MainViewHolder>() {
    private var listData: List<ProductOriginalRes> = ArrayList()
    var click: ((data : ProductOriginalRes)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ProductOriginalRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemManageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: ProductOriginalRes) {
            if(data.hinhanh != null){
                val strUrl: List<String> = data.hinhanh.split("3000/")
                val url = PathApi.BASE_URL + strUrl.get(1)
                Picasso.get().load(url).into(binding.imvMng)
            }
            binding.tvIdMng.text = data.masp
            binding.tvNameMng.text = data.tensp
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
//            var intent = Intent(holder.itemView.context, ProductMngActivity::class.java)
//            intent.putExtra("data",data)
//            holder.itemView.context.startActivity(intent)
        }
        holder.binding.deleteMng.setOnClickListener{
            click?.invoke(data)
        }

    }
}