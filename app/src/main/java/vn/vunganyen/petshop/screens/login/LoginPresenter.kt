package vn.vunganyen.petshop.screens.login

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiLoginService
import vn.vunganyen.petshop.data.model.login.LoginReq
import vn.vunganyen.petshop.data.model.login.LoginRes

class LoginPresenter {
    var loginInterface : LoginInterface

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
        println("1:"+req.tendangnhap)
        println("2:"+req.matkhau)
        ApiLoginService.Api.api.authLogin(req).enqueue(object :Callback<LoginRes>{
            override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                println("vô")
                if(response.isSuccessful){
                    LoginActivity.token = response.body()!!.accessToken
                    loginInterface.loginSuccess()
                }
                else{
                    println("Sai thông tin đăng nhập")
                }
            }

            override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                Log.d("error" , ""+call)
                println("error "+call)
                t.printStackTrace()
            }

        })
    }
}