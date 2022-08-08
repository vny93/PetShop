package vn.vunganyen.petshop.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.petshop.data.model.cart.add.AddCartRes
import vn.vunganyen.petshop.databinding.ItemOrderBinding
import vn.vunganyen.petshop.screens.home.main.HomeActivity
import java.text.DecimalFormat
import java.util.*

class AdapterCart : RecyclerView.Adapter<AdapterCart.MainViewHolder>() {
    private var listData: List<AddCartRes> = ArrayList()
    val formatter = DecimalFormat("###,###,###")

    fun setData(list: List<AddCartRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bindItem(data: AddCartRes) {
            binding.tvIdCart.setText(data.magh.toString())
            var mdate : Date = HomeActivity.formatdate2.parse(data.ngaydat)
            var strDate = HomeActivity.formatdate3.format(mdate)
            binding.tvBookDate.setText(strDate)
            val price = formatter.format(data.tongtien).toString() + " đ"
            binding.tvPriceOrder.setText(price)
            binding.tvStatus.setText(data.trangthai)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = listData[position]
        holder.bindItem(data)
//        holder.binding.cartAdd.setOnClickListener {
//        }
    }
}