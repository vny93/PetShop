package vn.vunganyen.petshop.screens.home

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.user.MainUserRes
import vn.vunganyen.petshop.databinding.ActivityHomeBinding
import vn.vunganyen.petshop.screens.account.FragmentAccount
import vn.vunganyen.petshop.screens.cart.FragmentCart
import vn.vunganyen.petshop.screens.explore.FragmentExplore
import vn.vunganyen.petshop.screens.login.LoginActivity
import java.util.regex.Pattern


class HomeActivity : AppCompatActivity(),HomeInterface {
    lateinit var binding: ActivityHomeBinding
    lateinit var homePresenter: HomePresenter

    companion object{
        //  var EMAIL_ADDRESS = Pattern.compile("^\\w+[\\w.]+@gmail.com$")
        var PASSWORD = Pattern.compile("^((?=.*[A-Z])(?=.*[a-z])(?=.*d)|(?=.*[a-z])(?=.*d)(?=.*[^A-Za-zd])|(?=.*[A-Z])(?=.*d)(?=.*[^A-Za-zd])|(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-zd])).{8,}\$")
        var SDT = Pattern.compile("(((\\+|)84)|0)(3|5|7|8|9)+([0-9]{8})")
        var token : String =""
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        lateinit var profile: MainUserRes
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPreferences()
        homePresenter = HomePresenter(this)
        checkShaharedPre()
        setEventBottomNav()
        replaceFragment(FragmentShop())

    }

    private fun initPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()
    }

    fun checkShaharedPre(){
        var tokenEditor = sharedPreferences.getString("token", "").toString()
        println("token lúc đầu: "+tokenEditor)
        if(!tokenEditor.equals("")){
            token = tokenEditor
            homePresenter.getProfileEditor()
        }
    }

    fun setEventBottomNav() {
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    replaceFragment(FragmentShop())
                    Toast.makeText(this, "Home Item reselected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_search -> {
                    replaceFragment(FragmentExplore())
                    Toast.makeText(this, "Search Item reselected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_cart -> {
                    replaceFragment(FragmentCart())
                    Toast.makeText(this, "Cart Item reselected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_account -> {
                    replaceFragment(FragmentAccount())
                    Toast.makeText(this, "Account Item reselected", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, fragment)
        transaction.commit()
    }
}