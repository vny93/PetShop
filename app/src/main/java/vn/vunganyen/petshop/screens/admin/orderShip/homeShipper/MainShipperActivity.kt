package vn.vunganyen.petshop.screens.admin.orderShip.homeShipper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityMainShipperBinding
import vn.vunganyen.petshop.screens.admin.orderShip.shipperListOrder.ShipperOrderActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class MainShipperActivity : AppCompatActivity(), MainShipperInterface {
    lateinit var binding : ActivityMainShipperBinding
    lateinit var mainShipperPresenter: MainShipperPresenter
    var dialog: StartAlertDialog = StartAlertDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainShipperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainShipperPresenter = MainShipperPresenter(this)
        checkShaharedPre()
        setData()
        setEvent()
    }

    fun checkShaharedPre(){
        var tokenEditor = SplashScreenActivity.sharedPreferences.getString("token", "").toString()
     //   var username = SplashScreenActivity.sharedPreferences.getString("username", "").toString()
        println("token lúc đầu: "+tokenEditor)
        if(!tokenEditor.equals("")){
            SplashScreenActivity.token = tokenEditor
            mainShipperPresenter.getProfileShipperEditor()
        }
    }

    fun setData(){
        binding.homeNameShipper.text = SplashScreenActivity.profileStaff.result.hoten
    }

    fun setEvent(){
        binding.imvLogout.setOnClickListener{
            dialog.showStartDialog4(getString(R.string.mess_logOut), this)
            dialog.clickOk = { ->
                SplashScreenActivity.token = ""
                SplashScreenActivity.editor.clear().apply()
                SplashScreenActivity.sharedPreferences.edit().clear().apply()
                println("đã xóa")
                var intent = Intent(this, SplashScreenActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnOrderShip.setOnClickListener{
            var intent = Intent(this, ShipperOrderActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.homeNameShipper.text = SplashScreenActivity.profileStaff.result.hoten
    }
}