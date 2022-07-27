package vn.vunganyen.petshop.screens.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.AdapterProduct
import vn.vunganyen.petshop.data.model.product.ProductReq
import vn.vunganyen.petshop.data.model.product.ProductRes
import vn.vunganyen.petshop.data.model.productType.ProductTypeRes
import vn.vunganyen.petshop.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity(), ProductInterface {
    lateinit var binding: ActivityProductBinding
    lateinit var productPresenter: ProductPresenter
    var adapter : AdapterProduct = AdapterProduct()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productPresenter = ProductPresenter(this)
        getData()
        setEvent()
     //   setToolbar()
    }

    fun getData(){
        var data : ProductTypeRes = getIntent().getSerializableExtra("data") as ProductTypeRes
        var req = ProductReq(data.maloaisp)
        binding.nameProductType.text = data.tenloaisp
        productPresenter.getListData(req)
    }

    fun setEvent(){
        binding.backProduct.setOnClickListener{
            finish()
        }
    }

//    fun setToolbar() {
//        var toolbar = binding.toolbarChangeInfor
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        finish()
//        return true
//    }


    override fun getListSuccess(list: List<ProductRes>) {
        adapter.setData(list)
        binding.rcvProduct.adapter = adapter
    }
}