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
import vn.vunganyen.petshop.screens.home.HomeActivity
import vn.vunganyen.petshop.screens.productDetail.ProDetailActivity
import java.text.DecimalFormat
import java.util.*

class AdapterCartDetail : RecyclerView.Adapter<AdapterCartDetail.MainViewHolder>() {
    private var listData: List<GetCDSpRes> = ArrayList()
    val formatter = DecimalFormat("###,###,###")
    var proDetail = ProDetailActivity()
    var clickOk: ((price : Float)->Unit)?=null
    var dialog : StartAlertDialog = StartAlertDialog()
    var slbandau = 0

    fun setData(list: List<GetCDSpRes>) {
        this.listData = list
        HomeActivity.sumPrice = 0.0f
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bindItem(data: GetCDSpRes) {
            if (data.hinhanh != null) {
                val strUrl: List<String> = data.hinhanh.split("3000/")
                var url = PathApi.BASE_URL + strUrl.get(1)
                Picasso.get().load(url).into(binding.imvCartPro)
            }
            binding.tvCartProname.setText(data.tensp)
            val price = formatter.format(data.gia * data.ctsoluong).toString() + " đ"
            binding.tvCartPrice.setText(price)
            binding.edtCartNumber.setText(data.ctsoluong.toString())
            slbandau = data.ctsoluong
            clickOk?.invoke(data.gia*data.ctsoluong)
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

    @SuppressLint("StringFormatMatches")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = listData[position]
        holder.bindItem(data)
        holder.binding.cartAdd.setOnClickListener {
            var soluong = holder.binding.edtCartNumber.text.toString().toInt()
            soluong = soluong + 1
            if(soluong > data.soluong){
                dialog.showStartDialog3(holder.itemView.context.getString(R.string.tv_numProDetail, data.soluong), holder.itemView.context)
            }
            else{
                clickOk?.invoke(data.gia)
                var req = PutCDReq(data.magh, data.masp, data.gia, soluong)
                update(holder.binding,req)
            }

        }

        holder.binding.cartMinus.setOnClickListener {
            var soluong = holder.binding.edtCartNumber.text.toString().toInt()
            soluong = soluong - 1
            if(soluong < 1){
                dialog.showStartDialog4(holder.itemView.context.getString(R.string.dialog_remove),holder.itemView.context)
                dialog.clickOk = {
                    -> //call api remove
                }
                //nếu xóa thì xóa không thì thôi
            }
            else{
                clickOk?.invoke(-(data.gia))
                var req =PutCDReq(data.magh, data.masp, data.gia, soluong)
                update(holder.binding,req)
            }

        }

//        holder.binding.edtCartNumber.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//            override fun afterTextChanged(p0: Editable?) {
//                var soluong = holder.binding.edtCartNumber.text.toString().toInt()
//                if (soluong < 1) {
//                    dialog.showStartDialog4(holder.itemView.context.getString(R.string.dialog_remove),holder.itemView.context)
//                    dialog.clickOk = {
//                        -> //call api remove
//                    }
//                    //nếu xóa thì xóa không thì thôi
//                }else if(soluong > data.soluong) {
//                    dialog.showStartDialog3(holder.itemView.context.getString(R.string.tv_numProDetail, data.soluong), holder.itemView.context)
//                }
//                else{
//                    var price = soluong - slbandau
//                    clickOk?.invoke(price*data.gia)
//                    var req =upCartDetailReq(data.magh, data.masp, data.gia, soluong)
//                    update(holder.binding,req)
//
//                }
//            }
//
//        })

    }

    fun update(binding: ItemCardBinding, data: PutCDReq ){
        binding.edtCartNumber.setText(data.ctsoluong.toString())
        slbandau = data.ctsoluong
        val price = formatter.format(data.gia * data.ctsoluong).toString() + " đ"
        binding.tvCartPrice.setText(price)
        proDetail.updateDataAdapter(data)
    }
}