package vn.vunganyen.petshop.screens.client.home.seeAllProduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.AdapterProduct
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.product.get.ProductRes
import vn.vunganyen.petshop.databinding.ActivitySeeAllBinding
import vn.vunganyen.petshop.screens.client.home.shop.FragmentShop

class SeeAllActivity : AppCompatActivity(),SeeAllInterface {
    lateinit var binding : ActivitySeeAllBinding
    lateinit var seeAllPresenter: SeeAllPresenter
    var adapter : AdapterProduct = AdapterProduct()
    var name =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeAllBinding.inflate(layoutInflater)
        setContentView(binding.root)
        seeAllPresenter = SeeAllPresenter(this)
        setData()
        setEvent()
    }

    fun setData(){
        var check = getIntent().getIntExtra("type",0)
        println("check: "+check)
        if(check == 1){
            binding.nameProductType.setText(getString(R.string.tv_rcvDiscout))
            adapter.setData(FragmentShop.listDiscount)
            binding.rcvSeeAll.adapter = adapter
        }
        else if(check ==2){
            binding.nameProductType.setText(R.string.tv_rcvNew)
            adapter.setData(FragmentShop.listIsNew)
            binding.rcvSeeAll.adapter = adapter
        }
        else if(check ==3){
            binding.nameProductType.setText(R.string.tv_rcvGood)
            adapter.setData(FragmentShop.listIsGood)
            binding.rcvSeeAll.adapter = adapter
        }
        else{
            var idBrand = getIntent().getStringExtra("id").toString()
            name = getIntent().getStringExtra("name").toString()
            var req = BrandDetailReq(idBrand)
            seeAllPresenter.getProductByBrand(req)
        }

    }

    fun setEvent(){
        binding.backSeeAll.setOnClickListener{
            finish()
        }
    }

    override fun getListSuccess(list: List<ProductRes>) {
        val title = getString(R.string.tv_brand)+" "+ name
        binding.nameProductType.setText(title)
        adapter.setData(list)
        binding.rcvSeeAll.adapter = adapter
    }
}