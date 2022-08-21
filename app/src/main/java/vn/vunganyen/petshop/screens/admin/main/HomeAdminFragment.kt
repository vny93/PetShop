package vn.vunganyen.petshop.screens.admin.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.databinding.FragmentHomeAdminBinding
import vn.vunganyen.petshop.databinding.FragmentShopBinding
import vn.vunganyen.petshop.screens.client.home.shop.ShopPresenter
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity


class HomeAdminFragment : Fragment() {
    lateinit var binding : FragmentHomeAdminBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeAdminBinding.inflate(inflater,container,false)
        setData()
        return binding.root
    }

    fun setData(){
        binding.homeNameAdmin.text = SplashScreenActivity.profileAdmin.result.hoten
    }

    override fun onResume() {
        super.onResume()
        binding.homeNameAdmin.text = SplashScreenActivity.profileAdmin.result.hoten
    }

}