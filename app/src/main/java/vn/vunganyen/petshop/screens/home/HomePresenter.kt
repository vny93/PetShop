package vn.vunganyen.petshop.screens.home

import com.google.gson.Gson
import vn.vunganyen.petshop.data.model.user.MainUserRes
import vn.vunganyen.petshop.screens.login.LoginActivity

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

    fun getProfileEditor(){
        var strResponse =  HomeActivity.sharedPreferences.getString("profile","")
        HomeActivity.profile = gson.fromJson(strResponse,MainUserRes::class.java)
    }
}