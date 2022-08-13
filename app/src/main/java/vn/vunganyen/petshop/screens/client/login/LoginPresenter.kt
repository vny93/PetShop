package vn.vunganyen.petshop.screens.client.login

import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiAuthService
import vn.vunganyen.petshop.data.api.ApiUserService
import vn.vunganyen.petshop.data.model.auth.login.LoginReq
import vn.vunganyen.petshop.data.model.auth.login.LoginRes
import vn.vunganyen.petshop.data.model.user.getProfile.MainUserRes
import vn.vunganyen.petshop.data.model.user.getProfile.UserReq
import vn.vunganyen.petshop.screens.client.home.main.HomeActivity

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
                    HomeActivity.editor.putString("token",response.body()!!.accessToken)

                    getProfile(response.body()!!.accessToken, UserReq(req.tendangnhap))
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

    fun getProfile(token : String, req: UserReq){
        ApiUserService.Api.api.authGetProfile(token, req).enqueue(object : Callback<MainUserRes>{
            override fun onResponse(call: Call<MainUserRes>, response: Response<MainUserRes>) {
                if(response.isSuccessful){
                   // HomeActivity.profile = response.body()!!

                    println("mã khách hàng login: "+response.body()!!.result.makh)
                    //Lưu
                    setProfile(response.body()!!)

                    loginInterface.loginSuccess()
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

    fun setProfile(response : MainUserRes){
        var strResponse = gson.toJson(response).toString()
        HomeActivity.editor.putString("profile",strResponse)
    }

}