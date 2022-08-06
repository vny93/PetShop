package vn.vunganyen.petshop.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail.GetCDSpRes
import vn.vunganyen.petshop.data.model.cartDetail.update.PutCDReq
import vn.vunganyen.petshop.data.model.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ItemCardBinding
import vn.vunganyen.petshop.databinding.ItemProductCheckoutBinding
import vn.vunganyen.petshop.screens.home.HomeActivity
import vn.vunganyen.petshop.screens.productDetail.ProDetailActivity
import java.text.DecimalFormat
import java.util.*

class AdapterProductCheckout : RecyclerView.Adapter<AdapterProductCheckout.MainViewHolder>() {
    private var listData: List<GetCDSpRes> = ArrayList()
    val formatter = DecimalFormat("###,###,###")
    var proDetail = ProDetailActivity()
    var checkOutCallApi: (()->Unit)?=null

    fun setData(list: List<GetCDSpRes>) {
        this.listData = list
        HomeActivity.sumPrice = 0.0f
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
            val price = formatter.format(data.gia * data.ctsoluong).toString() + " đ"
            binding.tvProPriceCheckout.setText(price)
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