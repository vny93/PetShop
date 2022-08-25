package vn.vunganyen.petshop.screens.admin.inputData.staff.customStaff

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiAuthService
import vn.vunganyen.petshop.data.api.ApiRoleService
import vn.vunganyen.petshop.data.api.ApiStaffService
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductRes
import vn.vunganyen.petshop.data.model.admin.role.MainRoleRes
import vn.vunganyen.petshop.data.model.admin.staff.auth.GetStatusRes
import vn.vunganyen.petshop.data.model.admin.staff.auth.PutRoleReq
import vn.vunganyen.petshop.data.model.admin.staff.getDetail.PostDetailStaffReq
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffRes
import vn.vunganyen.petshop.data.model.admin.staff.updateProfile.PutStaffReq
import vn.vunganyen.petshop.data.model.client.auth.changePw.ChangePwRes
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderRes
import vn.vunganyen.petshop.data.model.client.register.addAuth.AddAuthReq
import vn.vunganyen.petshop.data.model.client.register.addAuth.MainAddAuth
import vn.vunganyen.petshop.data.model.client.register.findAuth.FindAuthReq
import vn.vunganyen.petshop.data.model.client.register.findAuth.FindAuthRes
import vn.vunganyen.petshop.data.model.client.user.addProfile.AddProfileReq
import vn.vunganyen.petshop.data.model.client.user.findEmail.FindEmailReq
import vn.vunganyen.petshop.data.model.client.user.findEmail.FindEmailRes
import vn.vunganyen.petshop.data.model.client.user.findPhone.FindPhoneReq
import vn.vunganyen.petshop.data.model.client.user.findPhone.FindPhoneRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.util.*

class CustomStaffPresenter {
    var customStaffInterface : CustomStaffInterface

    constructor(customStaffInterface: CustomStaffInterface) {
        this.customStaffInterface = customStaffInterface
    }

    fun vailidCheckInsert(req1 : AddAuthReq, req2:PutStaffReq ){
        if (req1.tendangnhap.isEmpty() || req1.matkhau.isEmpty() || req1.maquyen == 0){
            customStaffInterface.Empty()
            return
        }
        if(!SplashScreenActivity.USERNAME.matcher(req1.tendangnhap).matches()){
            customStaffInterface.UserIllegal()
            return
        }
        if(!SplashScreenActivity.PASSWORD.matcher(req1.matkhau).matches()){
            customStaffInterface.PassIllegal()
            return
        }
        checkAccountExist(req1,req2)
    }

    fun validCheckUpdate(req: PutStaffReq) {
        if (req.hoten.isEmpty() || req.ngaysinh.isEmpty() || req.gioitinh.isEmpty() || req.diachi.isEmpty() ||
            req.email.isEmpty() || req.sdt.isEmpty()){
            customStaffInterface.Empty()
            return
        }
        if (!SplashScreenActivity.SDT.matcher(req.sdt).matches()) {
            customStaffInterface.PhoneIllegal()
            return
        }
        if (!SplashScreenActivity.EMAIL_ADDRESS.matcher(req.email).matches()) {
            customStaffInterface.EmailIllegal()
            return
        }
        val current : String = SplashScreenActivity.formatYear.format(Date())
        val str: List<String> = req.ngaysinh.split("-")
        var year = str.get(0).toInt()
        var old = current.toInt() - year
        if(old < 18){
            customStaffInterface.OrlError()
            return
        }
        if(CustomStaffMngActivity.typeStaff.equals("insert")){
            checkStaffIdExist(SplashScreenActivity.token,req)
        }
        else checkPhoneExist(SplashScreenActivity.token,req)

    }


    fun getListRole(token: String){
        ApiRoleService.Api.api.getListRole(token).enqueue(object : Callback<MainRoleRes>{
            override fun onResponse(call: Call<MainRoleRes>, response: Response<MainRoleRes>) {
                if(response.isSuccessful){
                    customStaffInterface.getRoleSuccess(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainRoleRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getRoleId(token : String, req: FindAuthReq){
        println(req.tendangnhap)
        ApiAuthService.Api.api.getRole(token,req).enqueue(object : Callback<GetStatusRes>{
            override fun onResponse(call: Call<GetStatusRes>, response: Response<GetStatusRes>) {
                if(response.isSuccessful){
                    customStaffInterface.getRoleId(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<GetStatusRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkAccountExist(req1: AddAuthReq,req2: PutStaffReq){
        var req = FindAuthReq(req1.tendangnhap)
        ApiAuthService.Api.api.findAuth(req).enqueue(object : Callback<FindAuthRes>{
            override fun onResponse(call: Call<FindAuthRes>, response: Response<FindAuthRes>) {
                if(response.isSuccessful){
                    customStaffInterface.AccountExist()
                }
                else{
                    validCheckUpdate(req2)
                }
            }
            override fun onFailure(call: Call<FindAuthRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun checkStaffIdExist(token : String,req : PutStaffReq){
        ApiStaffService.Api.api.getStaffDetail(token,PostDetailStaffReq(req.manv)).enqueue(object : Callback<MainStaffRes>{
            override fun onResponse(call: Call<MainStaffRes>, response: Response<MainStaffRes>) {
                if(response.isSuccessful){
                    customStaffInterface.StaffIdExist()
                }
                else checkPhoneExist(token,req)
            }

            override fun onFailure(call: Call<MainStaffRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkPhoneExist(token : String,req: PutStaffReq){
        ApiStaffService.Api.api.findPhone(token,FindPhoneReq(req.sdt)).enqueue(object : Callback<CheckProductRes>{
            override fun onResponse(call: Call<CheckProductRes>, response: Response<CheckProductRes>) {
                if(response.isSuccessful){
                    println(response.body()!!.result)
                    if(CustomStaffMngActivity.typeStaff.equals("insert")){
                        customStaffInterface.PhoneExist()
                    }
                    else{
                        if(CustomStaffMngActivity.staff.sdt.equals(response.body()!!.result)){
                            checkEmailExist(token,req)
                        }
                        else customStaffInterface.PhoneExist()
                    }
                }
                else checkEmailExist(token,req)
            }
            override fun onFailure(call: Call<CheckProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun checkEmailExist(token : String,req: PutStaffReq ){
        ApiStaffService.Api.api.findEmail(token,FindEmailReq(req.email)).enqueue(object : Callback<CheckProductRes>{
            override fun onResponse(call: Call<CheckProductRes>, response: Response<CheckProductRes>) {
                if(response.isSuccessful){
                    if(CustomStaffMngActivity.typeStaff.equals("insert")){
                        customStaffInterface.EmailExist()
                    }
                    else{
                        if(CustomStaffMngActivity.staff.email.equals(response.body()!!.result)){
                            customStaffInterface.CheckSuccess()
                        }
                        else customStaffInterface.EmailExist()
                    }
                }
                else customStaffInterface.CheckSuccess()
            }
            override fun onFailure(call: Call<CheckProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun insertAccount(req1: AddAuthReq,req2: PutStaffReq){
        ApiAuthService.Api.api.addAuth(req1).enqueue(object : Callback<MainAddAuth>{
            override fun onResponse(call: Call<MainAddAuth>, response: Response<MainAddAuth>) {
                if(response.isSuccessful){
                    insertStaff(req1,req2)
                }
                else customStaffInterface.AddAuthError()
            }

            override fun onFailure(call: Call<MainAddAuth>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun insertStaff(req1: AddAuthReq, req2:PutStaffReq){
        var token = SplashScreenActivity.token
        var request = StaffRes(req2.manv,req2.hoten,req2.gioitinh,req2.diachi,req2.ngaysinh,req2.sdt,
        req2.email,req1.tendangnhap)
        ApiStaffService.Api.api.insertStaff(token,request).enqueue(object : Callback<MainStaffRes>{
            override fun onResponse(call: Call<MainStaffRes>, response: Response<MainStaffRes>) {
                if(response.isSuccessful){
                    customStaffInterface.AddSucces()
                }
                else customStaffInterface.AddAuthError()
            }

            override fun onFailure(call: Call<MainStaffRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateStaff(req: PutStaffReq){
        var token = SplashScreenActivity.token
        ApiStaffService.Api.api.updatetStaff(token,req).enqueue(object : Callback<UserOrderRes>{
            override fun onResponse(call: Call<UserOrderRes>, response: Response<UserOrderRes>) {
                if(response.isSuccessful){
                    updateRole(token,req)
                }
                else customStaffInterface.UpdateError()
            }

            override fun onFailure(call: Call<UserOrderRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateRole(token : String, req:PutStaffReq ){
        var req = PutRoleReq(CustomStaffMngActivity.staff.tendangnhap,CustomStaffMngActivity.roleIdChange)
        ApiAuthService.Api.api.updateRole(token,req ).enqueue(object : Callback<ChangePwRes>{
            override fun onResponse(call: Call<ChangePwRes>, response: Response<ChangePwRes>) {
                if(response.isSuccessful){
                    customStaffInterface.UpdateSucces()
                }
                else customStaffInterface.UpdateError()
            }

            override fun onFailure(call: Call<ChangePwRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}

