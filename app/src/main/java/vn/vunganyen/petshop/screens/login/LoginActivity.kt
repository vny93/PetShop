package vn.vunganyen.petshop.screens.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityLoginBinding
import vn.vunganyen.petshop.screens.home.HomeActivity
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity(), LoginInterface {
    lateinit var binding : ActivityLoginBinding
    lateinit var loginPresenter: LoginPresenter
    var dialog : StartAlertDialog = StartAlertDialog()
    companion object{
      //  var EMAIL_ADDRESS = Pattern.compile("^\\w+[\\w.]+@gmail.com$")
        var PASSWORD = Pattern.compile("^((?=.*[A-Z])(?=.*[a-z])(?=.*d)|(?=.*[a-z])(?=.*d)(?=.*[^A-Za-zd])|(?=.*[A-Z])(?=.*d)(?=.*[^A-Za-zd])|(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-zd])).{8,}\$")
        var SDT = Pattern.compile("(((\\+|)84)|0)(3|5|7|8|9)+([0-9]{8})")
        var token : String =""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginPresenter = LoginPresenter(this)
        setEvent()
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

        }

        binding.scrollViewLogin.setOnClickListener{
            binding.edtUsername.clearFocus()
            binding.edtPassword.clearFocus()
            binding.scrollViewLogin.hideKeyboard()
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

    override fun loginEmpty() {
        dialog.showStartDialog3(getString(R.string.login_empty), this)
    }

    override fun userIllegal() {
        dialog.showStartDialog3(getString(R.string.user_illegal), this)
    }

    override fun loginSuccess() {
        dialog.showStartDialog3(getString(R.string.login_success), this)
        var intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        println("token: "+token)
    }
}