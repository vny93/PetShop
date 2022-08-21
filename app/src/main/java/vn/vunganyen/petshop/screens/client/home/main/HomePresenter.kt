package vn.vunganyen.petshop.screens.client.home.main

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiUserService
import vn.vunganyen.petshop.data.model.client.user.getProfile.MainUserRes
import vn.vunganyen.petshop.data.model.client.user.getProfile.UserReq
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

//    fun getProfileClient(token : String, req: UserReq){
//        ApiUserService.Api.api.authGetProfile(token, req).enqueue(object : Callback<MainUserRes> {
//            override fun onResponse(call: Call<MainUserRes>, response: Response<MainUserRes>) {
//                if(response.isSuccessful){
//                    // HomeActivity.profile = response.body()!!
//                    println("mã khách hàng login: "+response.body()!!.result.makh)
//                    //Lưu
//                    SplashScreenActivity.profileClient = response.body()!!
//                }
//            }
//            override fun onFailure(call: Call<MainUserRes>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
}