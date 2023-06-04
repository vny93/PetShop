package vn.vunganyen.petshop.screens.splashScreen

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.client.user.getProfile.MainUserRes
import vn.vunganyen.petshop.databinding.ActivitySplashScreenBinding
import vn.vunganyen.petshop.screens.admin.main.MainAdminActivity
import vn.vunganyen.petshop.screens.admin.orderShip.homeShipper.MainShipperActivity
import vn.vunganyen.petshop.screens.client.home.main.HomeActivity
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class SplashScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    lateinit var splashPresenter: SplashPresenter

    companion object {
        var ADMIN = 1
        var SHIPPER = 3
        var CLIENT = 5
        var PENDING = "Chờ duyệt"
        var DELIVERY = "Đang giao"
        var DELIVERED = "Đã giao"
        var CANCELLED = "Đã hủy"
        var API_KEY = "553145e6-0d26-4311-a252-c0f6e5f213ef"
        //Tên đăng nhập tối thiểu tám ký tự, ít nhất một chữ cái và một số
        var USERNAME = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")
        var EMAIL_ADDRESS = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        //Mât khẩu tối thiểu tám ký tự, ít nhất một chữ cái viết hoa, một chữ cái viết thường và một số
        var PASSWORD = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}\$")
        //var SDT = Pattern.compile("(((\\+|)84)|0)(3|5|7|8|9)+([0-9]{8})")
        var SDT = Pattern.compile("(84|0){1}(3|5|7|8|9){1}+([0-9]{8})")
        var SIZE = Pattern.compile("^([0-9]{1,})+x([0-9]{1,})+x([0-9]{1,})")
        var token: String = ""
        var roleId: Int = 0
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        lateinit var profileClient: MainUserRes
        lateinit var profileAdmin: MainStaffRes
        lateinit var profileStaff: MainStaffRes
        var sumPrice: Float = 0.0f
        var sumMass : Float = 0.00f
        var sumPriceShip : Float = 0.0f
        //170 Lê Văn Khương, Thới An, Quận 12, Thành phố Hồ Chí Minh, Việt Nam
        var STORE_LAT : Double = 10.8684163
        var STORE_LONG : Double = 106.65234149999999
        var IDSHOP = "CH1"
        val formatdate = SimpleDateFormat("yyyy-MM-dd")
        val formatdate1 = SimpleDateFormat("dd/MM/yyyy")
        val formatdate2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.ENGLISH)
        val formatdate3 = SimpleDateFormat("dd-MM-yyyy hh:mm")
        val formatdate4 = SimpleDateFormat("dd-MM-yyyy")
        val formatter = DecimalFormat("###,###,###")
        val formatMonthYear = SimpleDateFormat("M-yyyy")
        val formatMonth = SimpleDateFormat("MM")
        val formatYear = SimpleDateFormat("yyyy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPreferences()
        splashPresenter = SplashPresenter()
//        SplashScreenActivity.token = ""
//        SplashScreenActivity.editor.clear().apply()
//        SplashScreenActivity.sharedPreferences.edit().clear().apply()
//        println("đã xóa")
        checkShaharedPre()
    }

    private fun initPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()
    }

    fun checkShaharedPre() {
        println("có vô đây kh v ?")
        var roleIdEditor = sharedPreferences.getInt("roleId", 0)
        println("mã quyền lúc đầu: " + roleIdEditor)
        if(roleIdEditor != 0){
            roleId = roleIdEditor
            if (roleId == CLIENT) {
                println("Vô cline nha")
                moveClient()
            } else if (roleId == ADMIN) {
                println("Vô Admin nha")
                moveAdmin()
            }
            else if (roleId == SHIPPER) {
                println("Vô Shipper nha")
                moveShipper()
            }
        }
        else{
            moveClient()
        }
    }

    override fun onResume() {
        super.onResume()
        println("vô on resume nè")
        checkShaharedPre()
    }

    fun moveClient() {
        Handler().postDelayed({
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }, 3000)
    }

    fun moveAdmin() {
        Handler().postDelayed({
            var intent = Intent(this, MainAdminActivity::class.java)
            startActivity(intent)
        }, 3000)
    }

    fun moveShipper() {
        Handler().postDelayed({
            var intent = Intent(this, MainShipperActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}