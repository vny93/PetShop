package vn.vunganyen.petshop.screens.admin.inputData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import vn.vunganyen.petshop.databinding.ActivityInputDataBinding
import vn.vunganyen.petshop.screens.admin.inputData.brand.BrandMngActivity

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