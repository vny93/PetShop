package vn.vunganyen.petshop.screens.splashScreen

import com.google.gson.Gson
import vn.vunganyen.petshop.data.model.client.user.getProfile.MainUserRes

class SplashPresenter {
    var gson = Gson()

    fun getProfileClientEditor(){
        var strResponse =  SplashScreenActivity.sharedPreferences.getString("profileClient","")
        SplashScreenActivity.profileClient = gson.fromJson(strResponse, MainUserRes::class.java)
    }
}