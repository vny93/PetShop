package vn.vunganyen.petshop.screens.client.register.newRegister

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiAuthService
import vn.vunganyen.petshop.data.api.ApiRoleService
import vn.vunganyen.petshop.data.model.client.register.addAuth.AddAuthReq
import vn.vunganyen.petshop.data.model.client.register.addAuth.MainAddAuth
import vn.vunganyen.petshop.data.model.client.register.findAuth.FindAuthReq
import vn.vunganyen.petshop.data.model.client.register.findAuth.FindAuthRes
import vn.vunganyen.petshop.data.model.client.register.findRole.FindRoleReq
import vn.vunganyen.petshop.data.model.client.register.findRole.MainFindRole
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class RegisterPresenter {
    var registerInterface : RegisterInterface

    constructor(registerInterface: RegisterInterface) {
        this.registerInterface = registerInterface
    }

    fun validCheck(username : String, password : String){
        if(username.isEmpty() || password.isEmpty()){
            registerInterface.RgtEmpty()
            return
        }
        if(!SplashScreenActivity.USERNAME.matcher(username).matches()){
            registerInterface.UserIllegal()
            return
        }
        if(!SplashScreenActivity.PASSWORD.matcher(password).matches()){
            registerInterface.PassIllegal()
            return
        }
        //ổn rồi
        handle(username,password)
        //kiểm tra tên đăng nhập đã tồn tại chưa
    }


    fun handle(username: String, password : String){
        var req = FindAuthReq(username)
        ApiAuthService.Api.api.findAuth(req).enqueue(object : Callback<FindAuthRes>{
            override fun onResponse(call: Call<FindAuthRes>, response: Response<FindAuthRes>) {
                if(response.isSuccessful){
                    registerInterface.AccountExist()
                }
                else{
                    getRoleId(username,password)
                }
            }

            override fun onFailure(call: Call<FindAuthRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getRoleId(username : String, password : String){
        var roleName = "khách hàng"
        var req = FindRoleReq(roleName)
        ApiRoleService.Api.api.findRole(req).enqueue(object : Callback<MainFindRole>{
            override fun onResponse(call: Call<MainFindRole>, response: Response<MainFindRole>) {
                if(response.isSuccessful){
                    addAccount(username, password, response.body()!!.result.maquyen)
                }
                else registerInterface.NotFindRoleId()
            }

            override fun onFailure(call: Call<MainFindRole>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addAccount(username : String, password : String, roleId : Int){
        var req = AddAuthReq(username,password,roleId)
        ApiAuthService.Api.api.addAuth(req).enqueue(object : Callback<MainAddAuth>{
            override fun onResponse(call: Call<MainAddAuth>, response: Response<MainAddAuth>) {
                if(response.isSuccessful){
                    registerInterface.AddAuthSuccess(username, password)
                }
                else registerInterface.AddAuthError()
            }

            override fun onFailure(call: Call<MainAddAuth>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}