package vn.vunganyen.petshop.screens.admin.inputData.productType

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vn.vunganyen.petshop.databinding.ActivityProTypeMngBinding

class ProTypeMngActivity : AppCompatActivity() {
    lateinit var binding : ActivityProTypeMngBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProTypeMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}