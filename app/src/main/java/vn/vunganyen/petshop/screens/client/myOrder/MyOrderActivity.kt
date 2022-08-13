package vn.vunganyen.petshop.screens.client.myOrder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import vn.vunganyen.petshop.data.adapter.ViewPagerAdapter
import vn.vunganyen.petshop.databinding.ActivityMyOrderBinding

class MyOrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyOrderBinding
    lateinit var myViewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewPager()
        setEvent()
    }

    fun setViewPager(){
        myViewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = myViewPagerAdapter
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Chờ xác nhận"
                    }
                    1 -> {
                        tab.text = "Chờ lấy hàng"
                    }
                    2 -> {
                        tab.text = "Đang giao"
                    }
                    3 -> {
                        tab.text = "Đã giao"
                    }
                    4 -> {
                        tab.text = "Đã hủy"
                    }
                    5 -> {
                        tab.text = "Trả hàng"
                    }
                }
            }).attach()
    }

    fun setEvent(){
        binding.backOrder.setOnClickListener{
            finish()
        }
    }
}