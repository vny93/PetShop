package vn.vunganyen.petshop.screens.admin.orderShip.homeShipper

import com.google.gson.Gson
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.client.user.getProfile.MainUserRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class MainShipperPresenter {
    var mainShipperInterface : MainShipperInterface
    var gson = Gson()

    constructor(mainShipperInterface: MainShipperInterface) {
        this.mainShipperInterface = mainShipperInterface
    }

    fun getProfileShipperEditor(){
        var strResponse =  SplashScreenActivity.sharedPreferences.getString("profileShipper","")
        SplashScreenActivity.profileStaff = gson.fromJson(strResponse, MainStaffRes::class.java)
    }
}