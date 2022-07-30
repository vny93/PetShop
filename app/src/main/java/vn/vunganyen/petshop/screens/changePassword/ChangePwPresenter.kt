package vn.vunganyen.petshop.screens.changePassword

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiAuthService
import vn.vunganyen.petshop.data.model.auth.changePw.ChangePwRes
import vn.vunganyen.petshop.data.model.auth.login.LoginReq
import vn.vunganyen.petshop.data.model.register.findAuth.FindAuthRes
import vn.vunganyen.petshop.screens.home.HomeActivity

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
        if (HomeActivity.PASSWORD.matcher(pw1).matches() && HomeActivity.PASSWORD.matcher(pw2).matches())
        {
            if (pw1.equals(pw2)) {
                checkAccount(curentPw, pw1)
            } else changePwInterface.ErrorConfirmPw()
        } else changePwInterface.RegexPassword()
    }

    fun checkAccount(curentPw: String, pw1: String){
        var req = LoginReq(HomeActivity.profile.result.tendangnhap,curentPw)
        ApiAuthService.Api.api.checkAuth(HomeActivity.token, req).enqueue(object : Callback<FindAuthRes>{
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
        var req = LoginReq(HomeActivity.profile.result.tendangnhap, newPassword)
        ApiAuthService.Api.api.changePassword(HomeActivity.token,req).enqueue(object : Callback<ChangePwRes>{
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