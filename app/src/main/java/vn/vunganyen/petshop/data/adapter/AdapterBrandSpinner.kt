package vn.vunganyen.petshop.data.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.databinding.ItemBrandBinding
import vn.vunganyen.petshop.databinding.ItemSpinnerBrandBinding

class AdapterBrandSpinner(context: Context, resource: Int, objects: List<BrandDetailRes>) :
    ArrayAdapter<BrandDetailRes>(context, resource, objects) {

    lateinit var binding1 : ItemSpinnerBrandBinding
    lateinit var binding2 : ItemBrandBinding

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        binding1 = ItemSpinnerBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding1.tvSelectBrand.setText(getItem(position)!!.tenhang)
        return binding1.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        binding2 = ItemBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      //  binding2.tvNameBrand.setText(getItem(position)!!.tenhang)
        val strUrl: List<String> = getItem(position)!!.logo.split("3000/")
        val url = PathApi.BASE_URL + strUrl.get(1)
        Picasso.get().load(url).into(binding2.imvBrand)
        return binding2.root
    }
}