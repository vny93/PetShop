package vn.vunganyen.petshop.screens.admin.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityMainAdminBinding
import vn.vunganyen.petshop.databinding.HeaderNaviBinding

class MainAdminActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding : ActivityMainAdminBinding
    lateinit var toogle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    var dialog : StartAlertDialog = StartAlertDialog()
    var FRAGMENT_QR = 0
//        var FRAGMENT_PROFILE = 1
//    var FRAGMENT_HISTORY = 2
//    var FRAGMENT_HELP = 3
//    var FRAGMENT_COMPLAINT = 4
//    var mCurrentFragment = FRAGMENT_QR
    var backPressed : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationView()
    }

    fun setNavigationView() {
        val toolbar: Toolbar = binding.adToolbarMain
        drawerLayout = binding.drawerLayout
        toogle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        var navigationView: NavigationView = binding.navigationView
        navigationView.setNavigationItemSelectedListener(this)
    //    replaceFragment(FragmenQRCode())
        navigationView.menu.findItem(R.id.nax_1).setCheckable(true)
        val headerBinding: HeaderNaviBinding =HeaderNaviBinding.bind(binding.navigationView.getHeaderView(0))
        //picasso
//        if(LoginActivity.profile.data.imageUrl != null) {
//            val url = LoginActivity.profile.data.imageUrl
//            Picasso.get().load(url).into(headerBinding.ImvAvatar)
//        }
    /*
        headerBinding.tvadHeaderName.text = LoginActivity.profile.data.name
        headerBinding.tvadHeaderUser.text = LoginActivity.workspace.data.name
        headerBinding.tvadHeaderName.setOnClickListener {
            var intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(GravityCompat.START)
        }
     */
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.nax_1) {
//            if (mCurrentFragment != FRAGMENT_QR) {
//                replaceFragment(FragmenQRCode())
//                mCurrentFragment = FRAGMENT_QR
//            }
        } else if (id == R.id.nax_2) {
         //   var intent = Intent(this, HistoryActivity::class.java)
         //   startActivity(intent)
        } else if (id == R.id.nax_3) {
          //  var intent = Intent(this, ComHisActivity::class.java)
          //  startActivity(intent)
        } else if (id == R.id.nax_4) {
           // dialog.showStartDialog4(getString(R.string.quesionLogout),this)
           // dialog.clickOk = {->
                //mainPresent.logOut()
             //   LoginActivity.editor.clear().apply()
             //   LoginActivity.sharedPreferences.edit().clear().apply()
             //   finish()
           // }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return false
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.ad_content_frame, fragment)
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
            Toast.makeText(this,"Press back again to exit the application", Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }
}