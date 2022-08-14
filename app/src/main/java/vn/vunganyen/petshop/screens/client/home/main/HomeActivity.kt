package vn.vunganyen.petshop.screens.client.home.main

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.databinding.ActivityHomeBinding
import vn.vunganyen.petshop.screens.client.account.FragmentAccount
import vn.vunganyen.petshop.screens.client.cart.FragmentCart
import vn.vunganyen.petshop.screens.client.explore.FragmentExplore
import vn.vunganyen.petshop.screens.client.home.shop.FragmentShop
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity


class HomeActivity : AppCompatActivity(), HomeInterface {
    lateinit var binding: ActivityHomeBinding
    lateinit var homePresenter: HomePresenter
    var backPressed : Long = 0
/*    companion object{
        //Tên đăng nhập tối thiểu tám ký tự, ít nhất một chữ cái và một số
        var USERNAME = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
        var EMAIL_ADDRESS = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        //Mât khẩu tối thiểu tám ký tự, ít nhất một chữ cái viết hoa, một chữ cái viết thường và một số
        var PASSWORD = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}\$")
        var SDT = Pattern.compile("(((\\+|)84)|0)(3|5|7|8|9)+([0-9]{8})")
        var token : String =""
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        lateinit var profile: MainUserRes
        var sumPrice : Float = 0.0f
        val formatdate =  SimpleDateFormat("yyyy-MM-dd")
        val formatdate1 =  SimpleDateFormat("dd/MM/yyyy")
        val formatdate2 =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val formatdate3 =  SimpleDateFormat("dd-MM-yyyy hh:mm")
        val formatdate4 =  SimpleDateFormat("dd-MM-yyyy")
        val formatter = DecimalFormat("###,###,###")
    }

 */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
   //     initPreferences()
        homePresenter = HomePresenter(this)
        checkShaharedPre()
        setEventBottomNav()
        replaceFragment(FragmentShop())
    }

    private fun initPreferences() {
        SplashScreenActivity.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        SplashScreenActivity.editor = SplashScreenActivity.sharedPreferences.edit()
    }

    fun checkShaharedPre(){
        var tokenEditor = SplashScreenActivity.sharedPreferences.getString("token", "").toString()
        println("token lúc đầu: "+tokenEditor)
        if(!tokenEditor.equals("")){
            SplashScreenActivity.token = tokenEditor
            homePresenter.getProfileClientEditor()
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

    override fun onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            moveTaskToBack(true);
            // System.exit(1);
            finish();
        }
        else{
            Toast.makeText(this,"Press back again to exit the application",Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }
}