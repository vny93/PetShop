package vn.vunganyen.petshop.screens.changePassword

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityChangePassBinding

class ChangePassActivity : AppCompatActivity(), ChangePwInterface {
    lateinit var binding : ActivityChangePassBinding
    lateinit var changePwPresenter: ChangePwPresenter
    var dialog : StartAlertDialog = StartAlertDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changePwPresenter = ChangePwPresenter(this)
        setEvent()
    }

    fun setEvent(){
        binding.backChangePw.setOnClickListener{
            finish()
        }

        binding.btnChange.setOnClickListener{
            var curentPw = binding.edtCurrentPass.text.toString()
            var password1 = binding.edtNewPass.text.toString()
            var password2 = binding.edtPass2.text.toString()
            changePwPresenter.validCheck(curentPw,password1,password2)
        }
        binding.viewAccount.setOnClickListener({
            binding.edtCurrentPass.clearFocus()
            binding.edtNewPass.clearFocus()
            binding.edtPass2.clearFocus()
            binding.viewAccount.hideKeyboard()
        })
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

    override fun ErrorIsEmpty() {
        dialog.showStartDialog3(getString(R.string.account_empty),this)
    }

    override fun RegexPassword() {
        dialog.showStartDialog3(getString(R.string.PassIllegal),this)
    }

    override fun ErrorConfirmPw() {
        dialog.showStartDialog3(getString(R.string.account_errorConfirmPw),this)
    }

    override fun ErrorCurrentPw() {
        dialog.showStartDialog3(getString(R.string.account_errorCurrentPw),this)
    }

    override fun ChangePwSuccess() {
        binding.edtCurrentPass.setText("")
        binding.edtNewPass.setText("")
        binding.edtPass2.setText("")
        dialog.showStartDialog3(getString(R.string.account_changePwSuccess),this)
    }

    override fun ChangePwFail() {
        dialog.showStartDialog3(getString(R.string.account_changePwFail),this)
    }
}