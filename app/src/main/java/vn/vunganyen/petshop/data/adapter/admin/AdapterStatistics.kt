package vn.vunganyen.petshop.data.adapter.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.vunganyen.petshop.data.model.admin.cart.getTurnover.TurnoverRes
import vn.vunganyen.petshop.data.model.client.cart.add.AddCartRes
import vn.vunganyen.petshop.databinding.ItemOrderBinding
import vn.vunganyen.petshop.databinding.ItemStatisticsBinding
import vn.vunganyen.petshop.screens.admin.statistics.StatisticsActivity
import vn.vunganyen.petshop.screens.client.myOrderDetail.OrderDetailActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.text.DecimalFormat
import java.util.*

class AdapterStatistics : RecyclerView.Adapter<AdapterStatistics.MainViewHolder>() {
    private var listData: List<TurnoverRes> = ArrayList()
    val formatter = DecimalFormat("###,###,###")
    var click: ((price : Float)->Unit)?=null
    fun setData(list: List<TurnoverRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemStatisticsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bindItem(data: TurnoverRes) {
            var mdate : Date = SplashScreenActivity.formatMonth.parse(data.thang.toString())
            var month = SplashScreenActivity.formatMonth.format(mdate)
            binding.time.setText(month+"/"+data.nam.toString())
            StatisticsActivity.sum = StatisticsActivity.sum + data.doanhthu
            val price = formatter.format(data.doanhthu).toString() + " đ"
            binding.priceStatistics.setText(price)
            click?.invoke(data.doanhthu)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemStatisticsBinding.inflate(
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