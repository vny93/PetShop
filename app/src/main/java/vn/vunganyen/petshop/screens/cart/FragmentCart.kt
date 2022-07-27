package vn.vunganyen.petshop.screens.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.databinding.FragmentCartBinding
import vn.vunganyen.petshop.screens.home.HomeActivity
import vn.vunganyen.petshop.screens.login.LoginActivity

class FragmentCart : Fragment() {
    lateinit var binding: FragmentCartBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        val token = LoginActivity.token
        checkToken(token)
        setEvent()
        return binding.root
    }

    fun checkToken(token : String){
        if(token.equals("")){
            binding.rcvMyCard.visibility = View.GONE
            binding.btnCheckOut.visibility = View.GONE
        }
        else{
            binding.imvCartError.visibility = View.GONE
            binding.tvCartError.visibility = View.GONE
            binding.btnCartLogin.visibility = View.GONE
        }
    }

    fun setEvent(){
        binding.btnCartLogin.setOnClickListener{
            var intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}