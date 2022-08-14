package vn.vunganyen.petshop.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.client.product.get.ProductRes
import vn.vunganyen.petshop.databinding.ItemProductBinding
import vn.vunganyen.petshop.screens.client.productDetail.ProDetailActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity


class AdapterProduct : RecyclerView.Adapter<AdapterProduct.MainViewHolder>() {
    private var listData: List<ProductRes> = ArrayList()
    fun setData(list: List<ProductRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bindItem(data: ProductRes) {
            if (data.hinhanh != null) {
                val strUrl: List<String> = data.hinhanh.split("3000/")
                var url = PathApi.BASE_URL + strUrl.get(1)
                Picasso.get().load(url).into(binding.imvProduct)
            }
            binding.tvProdcutname.setText(data.tensp)
            //so sánh giá gốc và giá khuyến mãi
            if(data.giagiam == data.gia){
                val price = SplashScreenActivity.formatter.format(data.gia.toInt()).toString() + " đ"
                binding.tvPrice.setText(price)
            }
            else{
                val price = SplashScreenActivity.formatter.format(data.gia.toInt()).toString() + " đ"
                val priceDiscount = SplashScreenActivity.formatter.format(data.giagiam.toInt()).toString() + " đ"

                val spanned = SpannableString(price)
                spanned.setSpan(StrikethroughSpan(), 0, price.length, 0)
                binding.tvPriceDiscount.setText(spanned)
                binding.tvPrice.setText(priceDiscount)
            }

            if(data.soluong.toString().toInt() > 0){
                binding.tvStatus.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemProductBinding.inflate(
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