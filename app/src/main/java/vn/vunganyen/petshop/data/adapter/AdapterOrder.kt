package vn.vunganyen.petshop.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.client.cart.add.AddCartRes
import vn.vunganyen.petshop.databinding.ItemOrderBinding
import vn.vunganyen.petshop.screens.client.myOrderDetail.OrderDetailActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.text.DecimalFormat
import java.util.*

class AdapterOrder : RecyclerView.Adapter<AdapterOrder.MainViewHolder>() {
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
            var mdate : Date = SplashScreenActivity.formatdate2.parse(data.ngaydat)
            var strDate = SplashScreenActivity.formatdate3.format(mdate)
            binding.tvBookDate.setText(strDate)
            var mdate2 : Date = SplashScreenActivity.formatdate2.parse(data.ngaydukien)
            var strDate2 = SplashScreenActivity.formatdate4.format(mdate2)
            binding.tvDateReceive.setText(strDate2)
            val price = formatter.format(data.tongtien).toString() + " đ"
            binding.tvPriceOrder.setText(price)
            binding.tvStatus.setText(data.trangthai)
            if(data.trangthai.equals(SplashScreenActivity.DELIVERY)){
                binding.imvStatus.setBackground(itemView.context.getDrawable(R.drawable.logo_stt1))
            }
            else if(data.trangthai.equals(SplashScreenActivity.DELIVERED)){
                binding.imvStatus.setBackground(itemView.context.getDrawable(R.drawable.logo_stt2))
            }
            else if(data.trangthai.equals(SplashScreenActivity.CANCELLED)){
                binding.imvStatus.setBackground(itemView.context.getDrawable(R.drawable.logo_stt3))
            }        }
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
        holder.itemView.setOnClickListener{
            var intent = Intent(holder.itemView.context, OrderDetailActivity::class.java)
            intent.putExtra("magh",data.magh)
            holder.itemView.context.startActivity(intent)
        }
    }
}