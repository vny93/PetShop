package vn.vunganyen.petshop.screens.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.FragmentAccountBinding
import vn.vunganyen.petshop.screens.changePassword.ChangePassActivity
import vn.vunganyen.petshop.screens.home.HomeActivity
import vn.vunganyen.petshop.screens.login.LoginActivity
import vn.vunganyen.petshop.screens.loginRequired.LoginRequired
import vn.vunganyen.petshop.screens.myOrder.MyOrderActivity
import vn.vunganyen.petshop.screens.register.newProfile.ProfileActivity


class FragmentAccount : Fragment() {
    lateinit var binding: FragmentAccountBinding
    var dialog : StartAlertDialog = StartAlertDialog()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        setEvent()
        setData()
        return binding.root
    }

    fun setData(){
        if(!HomeActivity.token.equals("")){
            binding.tvHeaderName.text = HomeActivity.profile.result.hoten
            binding.tvHeaderUser.text = HomeActivity.profile.result.tendangnhap
        }
        else binding.btnLogOut.visibility = View.GONE
    }

    fun setEvent(){
        binding.btnLogOut.setOnClickListener{
            context?.let { it1 -> dialog.showStartDialog4(getString(R.string.mess_logOut), it1) }
            dialog.clickOk = {->
                HomeActivity.token = ""
                HomeActivity.editor.clear().apply()
                HomeActivity.sharedPreferences.edit().clear().apply()
                println("đã xóa")
                binding.tvHeaderName.text = getString(R.string.header_name)
                binding.tvHeaderUser.text = getString(R.string.header_username)
                binding.btnLogOut.visibility = View.GONE
            }
        }

        binding.imvEditProfile.setOnClickListener{
            if(HomeActivity.token.equals("")){
                context?.let { it1 -> dialog.showStartDialog4(getString(R.string.login_required), it1
                ) }
                dialog.clickOk = { ->
                    var intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                } }
            else{
                var intent = Intent(context, ProfileActivity::class.java)
                startActivity(intent)
            }
        }

        binding.imvOrder.setOnClickListener{
            if(HomeActivity.token.equals("")){
                context?.let { it1 -> dialog.showStartDialog4(getString(R.string.login_required), it1
                    ) }
                dialog.clickOk = { -> var intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                } }
            else{
                var intent = Intent(context, MyOrderActivity::class.java)
                startActivity(intent)
            }
        }

        binding.imvChangePass.setOnClickListener{
            if(HomeActivity.token.equals("")){
                context?.let { it1 ->dialog.showStartDialog4(getString(R.string.login_required),it1
                    )}
                dialog.clickOk = { ->var intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                }}
            else {
                var intent = Intent(context, ChangePassActivity::class.java)
                startActivity(intent)
            }
        }

        binding.imvHelp.setOnClickListener{
            if(HomeActivity.token.equals("")){
                context?.let { it1 ->dialog.showStartDialog4(getString(R.string.login_required),it1
                    ) }
                dialog.clickOk = { ->var intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                } }
        }

        binding.imvAbout.setOnClickListener{
            if(HomeActivity.token.equals("")){
                context?.let { it1 ->dialog.showStartDialog4(getString(R.string.login_required),it1
                    ) }
                dialog.clickOk = { -> var intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                } }
        }
    }

    override fun onResume() {
        super.onResume()
        if(!HomeActivity.token.equals("")){
            binding.tvHeaderName.text = HomeActivity.profile.result.hoten
            binding.tvHeaderUser.text = HomeActivity.profile.result.tendangnhap
        }
        else binding.btnLogOut.visibility = View.GONE
    }

}