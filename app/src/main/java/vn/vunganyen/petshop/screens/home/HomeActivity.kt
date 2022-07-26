package vn.vunganyen.petshop.screens.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.databinding.ActivityHomeBinding
import vn.vunganyen.petshop.screens.account.FragmentAccount
import vn.vunganyen.petshop.screens.cart.FragmentCart
import vn.vunganyen.petshop.screens.explore.FragmentExplore


class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEventBottomNav()
        replaceFragment(FragmentShop())


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