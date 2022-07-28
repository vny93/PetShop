package vn.vunganyen.petshop.screens.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.classSupport.StartAlertDialog
import vn.vunganyen.petshop.data.model.user.MainUserRes
import vn.vunganyen.petshop.databinding.FragmentAccountBinding
import vn.vunganyen.petshop.screens.home.HomeActivity


class FragmentAccount : Fragment() {
    lateinit var binding: FragmentAccountBinding
    var dialog : StartAlertDialog = StartAlertDialog()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        setEvent()
        return binding.root
    }

    fun setEvent(){
        binding.btnLogOut.setOnClickListener{
            context?.let { it1 -> dialog.showStartDialog4(getString(R.string.mess_logOut), it1) }
            dialog.clickOk = {->
                HomeActivity.token = ""
            //    HomeActivity.profile = MainUserRes("","","","") cần xóa profile luôn
                HomeActivity.editor.clear().apply()
                HomeActivity.sharedPreferences.edit().clear().apply()
                println("đã xóa")
                println("makh sau xóa:"+HomeActivity.profile.result.makh)
                println("tenkh sau xóa:"+HomeActivity.profile.result.hoten)
            }
        }
    }

}