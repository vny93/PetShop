package vn.vunganyen.petshop.data.adapter.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.admin.product.getList.ProductOriginalRes
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffRes
import vn.vunganyen.petshop.databinding.ItemManageBinding
import vn.vunganyen.petshop.databinding.ItemStaffBinding
import vn.vunganyen.petshop.screens.admin.inputData.mngProduct.customProduct.CustomPMngActivity
import vn.vunganyen.petshop.screens.admin.inputData.staff.customStaff.CustomStaffMngActivity


class AdapterStaffMng : RecyclerView.Adapter<AdapterStaffMng.MainViewHolder>() {
    private var listData: List<StaffRes> = ArrayList()
    var click: ((data : StaffRes)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<StaffRes>) {
        this.listData = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainViewHolder(val binding: ItemStaffBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: StaffRes) {
            binding.tvIdMng.text = data.manv
            binding.tvNameMng.text = data.hoten
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemStaffBinding.inflate(
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
            var intent = Intent(holder.itemView.context, CustomStaffMngActivity::class.java)
            intent.putExtra("type","update")
            intent.putExtra("data",data)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.deleteMng.setOnClickListener{
            click?.invoke(data)
        }

    }
}