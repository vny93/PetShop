package vn.vunganyen.petshop.screens.client.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.FragmentAccountBinding
import vn.vunganyen.petshop.screens.client.changePassword.ChangePassActivity
import vn.vunganyen.petshop.screens.client.login.LoginActivity
import vn.vunganyen.petshop.screens.client.myOrder.MyOrderActivity
import vn.vunganyen.petshop.screens.client.orderClient.OrderClientActivity
import vn.vunganyen.petshop.screens.client.register.newProfile.ProfileActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity


class FragmentAccount : Fragment() {
    lateinit var binding: FragmentAccountBinding
    var dialog: StartAlertDialog = StartAlertDialog()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        setEvent()
        setData()
        return binding.root
    }

    fun setData() {
        if (!SplashScreenActivity.token.equals("")) {
            binding.tvHeaderName.text = SplashScreenActivity.profileClient.result.hoten
            binding.tvHeaderUser.text = SplashScreenActivity.profileClient.result.tendangnhap
            binding.titleRequired.visibility = View.GONE
            binding.btnAcclogIn.visibility = View.GONE
            binding.imvCartError.visibility = View.GONE
        } else {
            binding.btnLogOut.visibility = View.GONE
            binding.lnlAccount.visibility = View.GONE
        }
    }

    fun setEvent() {
        binding.btnLogOut.setOnClickListener {
            context?.let { it1 -> dialog.showStartDialog4(getString(R.string.mess_logOut), it1) }
            dialog.clickOk = { ->
                SplashScreenActivity.token = ""
                SplashScreenActivity.editor.clear().apply()
                SplashScreenActivity.sharedPreferences.edit().clear().apply()
                println("đã xóa")
                binding.tvHeaderName.text = getString(R.string.header_name)
                binding.tvHeaderUser.text = getString(R.string.header_username)
                binding.btnLogOut.visibility = View.GONE
                binding.lnlAccount.visibility = View.GONE
                binding.titleRequired.visibility = View.VISIBLE
                binding.btnAcclogIn.visibility = View.VISIBLE
                binding.imvCartError.visibility = View.VISIBLE
            }
        }
        binding.btnAcclogIn.setOnClickListener {
            var intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.imvEditProfile.setOnClickListener {
            var intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.imvOrder.setOnClickListener {
            var intent = Intent(context, OrderClientActivity::class.java)
            startActivity(intent)
        }


        binding.imvChangePass.setOnClickListener {
            var intent = Intent(context, ChangePassActivity::class.java)
            startActivity(intent)

        }

        binding.imvHelp.setOnClickListener {
            dialog.clickOk = { ->
                var intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }


//        binding.imvAbout.setOnClickListener {
//            dialog.clickOk = { ->
//                var intent = Intent(context, LoginActivity::class.java)
//                startActivity(intent)
//            }
//        }
    }


    override fun onResume() {
        super.onResume()
        if (!SplashScreenActivity.token.equals("")) {
            binding.tvHeaderName.text = SplashScreenActivity.profileClient.result.hoten
            binding.tvHeaderUser.text = SplashScreenActivity.profileClient.result.tendangnhap
            binding.btnAcclogIn.visibility = View.GONE
        } else binding.lnlAccount.visibility = View.GONE
    }

}