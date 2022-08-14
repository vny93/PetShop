package vn.vunganyen.petshop.screens.client.register.newRegister

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityRegisterBinding
import vn.vunganyen.petshop.screens.client.register.newProfile.ProfileActivity

class RegisterActivity : AppCompatActivity(), RegisterInterface {
    lateinit var binding : ActivityRegisterBinding
    lateinit var registerPresenter: RegisterPresenter
    var dialog : StartAlertDialog = StartAlertDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerPresenter = RegisterPresenter(this)
        setEvent()
    }

    fun setEvent(){
        binding.btnRegister.setOnClickListener{
            var username = binding.rgtUsername.text.toString().toLowerCase()
            var password = binding.rgtPassword.text.toString()
            registerPresenter.validCheck(username,password)
        }

        binding.lnlRegister.setOnClickListener{
            binding.rgtUsername.clearFocus()
            binding.rgtPassword.clearFocus()
            binding.lnlRegister.hideKeyboard()
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

    override fun RgtEmpty() {
        dialog.showStartDialog3(getString(R.string.RgtEmpty),this)
    }

    override fun UserIllegal() {
        dialog.showStartDialog3(getString(R.string.UserIllegal),this)
    }

    override fun PassIllegal() {
        dialog.showStartDialog3(getString(R.string.PassIllegal),this)
    }

    override fun AccountExist() {
        dialog.showStartDialog3(getString(R.string.AccountExist),this)
    }

    override fun NotFindRoleId() {
        dialog.showStartDialog3(getString(R.string.NotFindRoleId),this)
    }

    override fun AddAuthSuccess(username : String, pass: String) {
        dialog.showStartDialog2(getString(R.string.AddAuthSuccess),this)
        dialog.clickOk = {
            -> var intent = Intent(this, ProfileActivity::class.java)
            println("username: "+username)
            intent.putExtra("username",username)
            intent.putExtra("password",pass)
            startActivity(intent)
        }
    }

    override fun AddAuthError() {
        dialog.showStartDialog3(getString(R.string.AddAuthError),this)
    }
}