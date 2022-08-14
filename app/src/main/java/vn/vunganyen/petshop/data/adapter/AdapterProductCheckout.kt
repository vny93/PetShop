package vn.vunganyen.petshop.data.adapter

import android.annotation.SuppressLint
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetCDSpRes
import vn.vunganyen.petshop.databinding.ItemProductCheckoutBinding
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.text.DecimalFormat
import java.util.*

class AdapterProductCheckout : RecyclerView.Adapter<AdapterProductCheckout.MainViewHolder>() {
    private var listData: List<GetCDSpRes> = ArrayList()
    val formatter = DecimalFormat("###,###,###")

    fun setData(list: List<GetCDSpRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemProductCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bindItem(data: GetCDSpRes) {
            if (data.hinhanh != null) {
                val strUrl: List<String> = data.hinhanh.split("3000/")
                var url = PathApi.BASE_URL + strUrl.get(1)
                Picasso.get().load(url).into(binding.imvProCheckout)
            }
            binding.tvPronameCheckout.setText(data.tensp)

            //so sánh giá gốc và giá khuyến mãi
            if(data.ctgia == data.gia){
                val price = SplashScreenActivity.formatter.format(data.ctgia.toInt()).toString() + " đ"
                binding.tvProPriceCheckout.setText(price)
            }
            else{
                val price = SplashScreenActivity.formatter.format(data.gia.toInt()).toString() + " đ"
                val priceDiscount = SplashScreenActivity.formatter.format(data.ctgia.toInt()).toString() + " đ"

                val spanned = SpannableString(price)
                spanned.setSpan(StrikethroughSpan(), 0, price.length, 0)
                binding.tvDiscountCheckout.setText(spanned)
                binding.tvProPriceCheckout.setText(priceDiscount)
            }
            //val price = formatter.format(data.ctgia * data.ctsoluong).toString() + " đ"
            //binding.tvProPriceCheckout.setText(price)
            binding.tvAmountCheckout.setText("x"+data.ctsoluong.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemProductCheckoutBinding.inflate(
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
    }
}