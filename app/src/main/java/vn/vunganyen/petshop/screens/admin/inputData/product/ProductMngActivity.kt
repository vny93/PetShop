package vn.vunganyen.petshop.screens.admin.inputData.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vn.vunganyen.petshop.databinding.ActivityProductMngBinding

class ProductMngActivity : AppCompatActivity() {
    lateinit var binding : ActivityProductMngBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}