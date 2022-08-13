package vn.vunganyen.petshop.screens.client.loginRequired

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vn.vunganyen.petshop.databinding.ActivityLoginRequiredBinding
import vn.vunganyen.petshop.screens.client.login.LoginActivity

class LoginRequired : AppCompatActivity() {
    lateinit var binding : ActivityLoginRequiredBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginRequiredBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()
    }

    fun setEvent(){
        binding.imvClose.setOnClickListener{
            finish()
        }

        binding.btnLoginRequired.setOnClickListener{
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}