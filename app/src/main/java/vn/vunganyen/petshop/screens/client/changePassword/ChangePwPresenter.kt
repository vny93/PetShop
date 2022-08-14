package vn.vunganyen.petshop.screens.client.changePassword

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiAuthService
import vn.vunganyen.petshop.data.model.client.auth.changePw.ChangePwRes
import vn.vunganyen.petshop.data.model.client.auth.login.LoginReq
import vn.vunganyen.petshop.data.model.client.register.findAuth.FindAuthRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class ChangePwPresenter {
    var changePwInterface : ChangePwInterface

    constructor(changePwInterface: ChangePwInterface) {
        this.changePwInterface = changePwInterface
    }

    fun validCheck(curentPw: String, pw1: String, pw2: String) {
        if (curentPw.isEmpty() || pw1.isEmpty() || pw2.isEmpty()) {
            changePwInterface.ErrorIsEmpty()
            return
        }
        if (SplashScreenActivity.PASSWORD.matcher(pw1).matches() && SplashScreenActivity.PASSWORD.matcher(pw2).matches())
        {
            if (pw1.equals(pw2)) {
                checkAccount(curentPw, pw1)
            } else changePwInterface.ErrorConfirmPw()
        } else changePwInterface.RegexPassword()
    }

    fun checkAccount(curentPw: String, pw1: String){
        var req = LoginReq(SplashScreenActivity.profileClient.result.tendangnhap,curentPw)
        ApiAuthService.Api.api.checkAuth(SplashScreenActivity.token, req).enqueue(object : Callback<FindAuthRes>{
            override fun onResponse(call: Call<FindAuthRes>, response: Response<FindAuthRes>) {
                if(response.isSuccessful){
                    changePassword(pw1)
                }
                else changePwInterface.ErrorCurrentPw()
            }

            override fun onFailure(call: Call<FindAuthRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun changePassword(newPassword : String){
        var req = LoginReq(SplashScreenActivity.profileClient.result.tendangnhap, newPassword)
        ApiAuthService.Api.api.changePassword(SplashScreenActivity.token,req).enqueue(object : Callback<ChangePwRes>{
            override fun onResponse(call: Call<ChangePwRes>, response: Response<ChangePwRes>) {
                if(response.isSuccessful){
                    changePwInterface.ChangePwSuccess()
                }
                else changePwInterface.ChangePwFail()
            }

            override fun onFailure(call: Call<ChangePwRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}