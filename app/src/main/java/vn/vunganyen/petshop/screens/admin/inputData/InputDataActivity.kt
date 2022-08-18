package vn.vunganyen.petshop.screens.admin.inputData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import vn.vunganyen.petshop.databinding.ActivityInputDataBinding
import vn.vunganyen.petshop.screens.admin.inputData.mngBrand.listBrand.BrandMngActivity
import vn.vunganyen.petshop.screens.admin.inputData.mngProduct.product.ProductMngActivity
import vn.vunganyen.petshop.screens.admin.inputData.mngProductType.listPT.ProTypeMngActivity

class InputDataActivity : AppCompatActivity() {
    lateinit var binding : ActivityInputDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        setEvent()
    }

    fun setEvent(){
        binding.btnBrandMng.setOnClickListener{
            var intent = Intent(this, BrandMngActivity::class.java)
            startActivity(intent)
        }
        binding.btnProMng.setOnClickListener {
            var intent = Intent(this, ProductMngActivity::class.java)
            startActivity(intent)
        }
        binding.btnProtypeMng.setOnClickListener {
            var intent = Intent(this, ProTypeMngActivity::class.java)
            startActivity(intent)
        }
    }

    fun setToolbar() {
        var toolbar = binding.toolbarInputData
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}