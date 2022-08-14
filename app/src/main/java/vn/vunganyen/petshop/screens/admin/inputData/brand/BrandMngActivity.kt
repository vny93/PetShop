package vn.vunganyen.petshop.screens.admin.inputData.brand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.data.adapter.admin.AdapterBrandMng
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.databinding.ActivityBrandMngBinding

class BrandMngActivity : AppCompatActivity(), BrandMngInterface {
    lateinit var binding : ActivityBrandMngBinding
    lateinit var brandMngPresenter: BrandMngPresenter
    var adapter : AdapterBrandMng = AdapterBrandMng()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrandMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        brandMngPresenter = BrandMngPresenter(this)
        getData()
        setToolbar()
    }

    fun getData(){
        brandMngPresenter.getListBrand()
    }

    override fun getListBrand(list: List<BrandDetailRes>) {
        adapter.setData(list)
        binding.rcvListBrand.adapter = adapter
        binding.rcvListBrand.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    fun setToolbar() {
        var toolbar = binding.toolbarListBrand
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun onResume() {
        super.onResume()
        brandMngPresenter.getListBrand()
    }
}