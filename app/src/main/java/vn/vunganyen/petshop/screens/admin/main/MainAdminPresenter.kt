package vn.vunganyen.petshop.screens.admin.main

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiStaffService
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffReq
import vn.vunganyen.petshop.data.model.client.user.getProfile.MainUserRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class MainAdminPresenter {
    var mainAdminInterface : MainAdminInterface
    var gson = Gson()

    constructor(mainAdminInterface: MainAdminInterface) {
        this.mainAdminInterface = mainAdminInterface
    }

    fun getProfileAdminEditor(){
        var strResponse =  SplashScreenActivity.sharedPreferences.getString("profileAdmin","")
        SplashScreenActivity.profileAdmin = gson.fromJson(strResponse, MainStaffRes::class.java)
    }

//    fun getProfileAdmin(token : String, req: StaffReq){
//        ApiStaffService.Api.api.authGetProfileStaff(token, req).enqueue(object : Callback<MainStaffRes> {
//            override fun onResponse(call: Call<MainStaffRes>, response: Response<MainStaffRes>) {
//                if(response.isSuccessful){
//                    println("mã admin login: "+response.body()!!.result.manv)
//                    //Lưu
//                    mainAdminInterface.getProfileSuccess(response.body()!!)
//                }
//            }
//            override fun onFailure(call: Call<MainStaffRes>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
}