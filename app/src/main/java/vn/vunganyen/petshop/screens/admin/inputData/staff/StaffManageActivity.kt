package vn.vunganyen.petshop.screens.admin.inputData.staff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vn.vunganyen.petshop.databinding.ActivityStaffManageBinding

class StaffManageActivity : AppCompatActivity() {
    lateinit var binding : ActivityStaffManageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaffManageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}