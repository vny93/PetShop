package vn.vunganyen.petshop.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail.CartDetailSpRes
import vn.vunganyen.petshop.databinding.ItemCardBinding
import vn.vunganyen.petshop.screens.productDetail.ProDetailActivity
import java.text.DecimalFormat
import java.util.*

class AdapterCartDetail : RecyclerView.Adapter<AdapterCartDetail.MainViewHolder>() {
    private var listData: List<CartDetailSpRes> = ArrayList()
    fun setData(list: List<CartDetailSpRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bindItem(data: CartDetailSpRes) {
            if (data.hinhanh != null) {
                val strUrl: List<String> = data.hinhanh.split("3000/")
                var url = PathApi.BASE_URL + strUrl.get(1)
                Picasso.get().load(url).into(binding.imvCartPro)
            }
            binding.tvCartProname.setText(data.tensp)
            val formatter = DecimalFormat("###,###,###")
            val price = formatter.format(data.gia.toInt()).toString() + " đ"
            binding.tvCartPrice.setText(price)
            binding.edtCartNumber.setText(data.soluong.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemCardBinding.inflate(
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
            var intent = Intent(holder.itemView.context, ProDetailActivity::class.java)
            intent.putExtra("id",data.masp)
            holder.itemView.context.startActivity(intent)
        }

    }
}