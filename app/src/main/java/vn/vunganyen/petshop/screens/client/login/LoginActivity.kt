package vn.vunganyen.petshop.screens.client.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityLoginBinding
import vn.vunganyen.petshop.screens.admin.main.MainAdminActivity
import vn.vunganyen.petshop.screens.admin.orderShip.homeShipper.MainShipperActivity
import vn.vunganyen.petshop.screens.client.home.main.HomeActivity
import vn.vunganyen.petshop.screens.client.register.newProfile.ProfileActivity
import vn.vunganyen.petshop.screens.client.register.newRegister.RegisterActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class LoginActivity : AppCompatActivity(), LoginInterface {
    lateinit var binding : ActivityLoginBinding
    lateinit var loginPresenter: LoginPresenter
    var backPressed : Long = 0
    var dialog : StartAlertDialog = StartAlertDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginPresenter = LoginPresenter(this)
        setEvent()
        callRegisterInvoke()

    }

    fun setEvent(){
        binding.btnLogin.setOnClickListener{
            var user = binding.edtUsername.text.toString().lowercase().trim()
            var password = binding.edtPassword.text.toString()
            loginPresenter.checkEmpty(user,password)
        }

        binding.forgotPassword.setOnClickListener{

        }

        binding.singup.setOnClickListener{
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.imvCloseLogin.setOnClickListener{
            finish()
        }

        binding.scrollViewLogin.setOnClickListener{
            binding.edtUsername.clearFocus()
            binding.edtPassword.clearFocus()
            binding.scrollViewLogin.hideKeyboard()
        }
    }

    fun callRegisterInvoke(){
        ProfileActivity.clickOk = {
                data -> loginPresenter.handle(data)
        }
    }


    fun View.hideKeyboard(): Boolean {
        try {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        } catch (ignored: RuntimeException) {
        }
        return false
    }

    override fun onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            moveTaskToBack(true)
            finish();
        }
        else{
            Toast.makeText(this,"Press back again to exit the application", Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }

    override fun loginEmpty() {
        dialog.showStartDialog3(getString(R.string.login_empty), this)
    }

//    override fun userIllegal() {
//        dialog.showStartDialog3(getString(R.string.user_illegal), this)
//    }

    override fun loginClientSuccess() {
        SplashScreenActivity.editor.commit()
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
     //   println("token: "+token)
    //    println("makh: "+ profile.result.makh)
    }

    override fun loginStaffSuccess() {
        SplashScreenActivity.editor.commit()
        var intent = Intent(this, MainAdminActivity::class.java)
        startActivity(intent)
    }

    override fun loginShipperSuccess() {
        SplashScreenActivity.editor.commit()
        var intent = Intent(this, MainShipperActivity::class.java)
        startActivity(intent)
    }

    override fun tokendie() {
        dialog.showStartDialog3(getString(R.string.tokendie), this)
    }

    override fun loginError() {
        dialog.showStartDialog3(getString(R.string.login_error), this)
    }

    override fun wrongPass() {
        dialog.showStartDialog3(getString(R.string.wrong_account), this)
    }
}