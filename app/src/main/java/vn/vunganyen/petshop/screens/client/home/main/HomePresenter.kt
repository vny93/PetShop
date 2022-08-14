package vn.vunganyen.petshop.screens.client.home.main

import com.google.gson.Gson
import vn.vunganyen.petshop.data.model.client.user.getProfile.MainUserRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class HomePresenter {
    var homeInterface : HomeInterface
    var gson = Gson()

    constructor(homeInterface: HomeInterface) {
        this.homeInterface = homeInterface
    }

//    fun setProfile(response : MainUserRes){
//        var strResponse = gson.toJson(response).toString()
//        HomeActivity.editor.putString("profile",strResponse)
//    }

    fun getProfileClientEditor(){
        var strResponse =  SplashScreenActivity.sharedPreferences.getString("profileClient","")
        SplashScreenActivity.profileClient = gson.fromJson(strResponse, MainUserRes::class.java)
    }
}