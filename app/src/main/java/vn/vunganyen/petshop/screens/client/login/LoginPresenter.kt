package vn.vunganyen.petshop.screens.client.login

import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiAuthService
import vn.vunganyen.petshop.data.api.ApiStaffService
import vn.vunganyen.petshop.data.api.ApiUserService
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffReq
import vn.vunganyen.petshop.data.model.client.auth.login.LoginReq
import vn.vunganyen.petshop.data.model.client.auth.login.LoginRes
import vn.vunganyen.petshop.data.model.client.user.getProfile.MainUserRes
import vn.vunganyen.petshop.data.model.client.user.getProfile.UserReq
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class LoginPresenter {
    var loginInterface : LoginInterface
    var gson = Gson()

    constructor(loginInterface: LoginInterface) {
        this.loginInterface = loginInterface
    }

    fun checkEmpty(user: String, pw: String) {
        if (user.isEmpty() || pw.isEmpty()) {
            loginInterface.loginEmpty()
            return
        }
//        if (!LoginActivity.EMAIL_ADDRESS.matcher(user).matches()) {
//            loginInterface.userIllegal()
//            return
//        }
        var req = LoginReq(user,pw)
        handle(req)
    }

    fun handle(req : LoginReq){
        ApiAuthService.Api.api.authLogin(req).enqueue(object :Callback<LoginRes>{
            override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                if(response.isSuccessful){
                   // HomeActivity.token = response.body()!!.accessToken
                    //lưu
                    SplashScreenActivity.editor.putString("token",response.body()!!.accessToken)
                    SplashScreenActivity.editor.putInt("roleId",response.body()!!.maquyen)
                    println("roleId: "+response.body()!!.maquyen)
                    if(response.body()!!.maquyen == 4){
                        println("vô user")
                        getProfileClient(response.body()!!.accessToken, UserReq(req.tendangnhap))
                    }
                    else if(response.body()!!.maquyen == 1){
                        println("vô admin")
                        getProfileAdmin(response.body()!!.accessToken, StaffReq(req.tendangnhap))
                    }
                }
                else{
                   loginInterface.wrongPass()
                }
            }

            override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                Log.d("error" , ""+call)
                println("error "+call)
                t.printStackTrace()
            }

        })
    }

    fun getProfileClient(token : String, req: UserReq){
        ApiUserService.Api.api.authGetProfile(token, req).enqueue(object : Callback<MainUserRes>{
            override fun onResponse(call: Call<MainUserRes>, response: Response<MainUserRes>) {
                if(response.isSuccessful){
                   // HomeActivity.profile = response.body()!!
                    println("mã khách hàng login: "+response.body()!!.result.makh)
                    //Lưu
                    setProfileClient(response.body()!!)

                    loginInterface.loginClientSuccess()
                }
                else{
                    loginInterface.tokendie()
                }
            }
            override fun onFailure(call: Call<MainUserRes>, t: Throwable) {
                loginInterface.loginError()
            }

        })
    }

    fun getProfileAdmin(token : String, req: StaffReq){
        ApiStaffService.Api.api.authGetProfileStaff(token, req).enqueue(object : Callback<MainStaffRes>{
            override fun onResponse(call: Call<MainStaffRes>, response: Response<MainStaffRes>) {
                if(response.isSuccessful){
                    // HomeActivity.profile = response.body()!!
                    println("mã admin login: "+response.body()!!.result.manv)
                    //Lưu
                    setProfileAdmin(response.body()!!)

                    loginInterface.loginStaffSuccess()
                }
                else{
                    loginInterface.tokendie()
                }
            }
            override fun onFailure(call: Call<MainStaffRes>, t: Throwable) {
                loginInterface.loginError()
            }

        })
    }

    fun setProfileClient(response : MainUserRes){
        var strResponse = gson.toJson(response).toString()
        SplashScreenActivity.editor.putString("profileClient",strResponse)
    }

    fun setProfileAdmin(response : MainStaffRes){
        var strResponse = gson.toJson(response).toString()
        SplashScreenActivity.editor.putString("profileAdmin",strResponse)
    }

}