package vn.vunganyen.petshop.screens.admin.profile.information

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiStaffService
import vn.vunganyen.petshop.data.api.ApiUserService
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductRes
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffReq
import vn.vunganyen.petshop.data.model.admin.staff.updateProfile.PutStaffReq
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderRes
import vn.vunganyen.petshop.data.model.client.user.addProfile.AddProfileReq
import vn.vunganyen.petshop.data.model.client.user.findEmail.FindEmailReq
import vn.vunganyen.petshop.data.model.client.user.findPhone.FindPhoneReq
import vn.vunganyen.petshop.data.model.client.user.findPhone.FindPhoneRes
import vn.vunganyen.petshop.data.model.client.user.getProfile.MainUserRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.util.*

class InforPresenter {
    var inforInterface : InforInterface
    var gson = Gson()

    constructor(inforInterface: InforInterface) {
        this.inforInterface = inforInterface
    }

    fun validCheck(req: PutStaffReq) {
        if (req.hoten.isEmpty() || req.ngaysinh.isEmpty() || req.gioitinh.isEmpty() || req.diachi.isEmpty() ||
            req.email.isEmpty() || req.sdt.isEmpty()){
            inforInterface.Empty()
            return
        }
        if (!SplashScreenActivity.SDT.matcher(req.sdt).matches()) {
            inforInterface.PhoneIllegal()
            return
        }
        if (!SplashScreenActivity.EMAIL_ADDRESS.matcher(req.email).matches()) {
            inforInterface.EmailIllegal()
            return
        }
        val current : String = SplashScreenActivity.formatYear.format(Date())
        val str: List<String> = req.ngaysinh.split("-")
        var year = str.get(0).toInt()
        var old = current.toInt() - year
        if(old < 18){
            inforInterface.OrlError()
            return
        }
        checkPhoneExist(SplashScreenActivity.token,req)
    }

    fun checkPhoneExist(token : String, req: PutStaffReq){
        ApiStaffService.Api.api.findPhone(token, FindPhoneReq(req.sdt)).enqueue(object : Callback<CheckProductRes>{
            override fun onResponse(call: Call<CheckProductRes>, response: Response<CheckProductRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result == SplashScreenActivity.profileAdmin.result.sdt){
                        checkEmailExist(token, req)
                    }
                    else inforInterface.PhoneExist()
                }
                else checkEmailExist(token,req)
            }
            override fun onFailure(call: Call<CheckProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun checkEmailExist(token : String, req:PutStaffReq ){
        ApiStaffService.Api.api.findEmail(token, FindEmailReq(req.email)).enqueue(object : Callback<CheckProductRes>{
            override fun onResponse(call: Call<CheckProductRes>, response: Response<CheckProductRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result == SplashScreenActivity.profileAdmin.result.email){
                        updateStaff(token,req)
                    }
                    else inforInterface.EmailExist()
                }
                else updateStaff(token,req)
            }
            override fun onFailure(call: Call<CheckProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun updateStaff(token : String, req: PutStaffReq){
        ApiStaffService.Api.api.updatetStaff(token,req).enqueue(object : Callback<UserOrderRes>{
            override fun onResponse(call: Call<UserOrderRes>, response: Response<UserOrderRes>) {
                if(response.isSuccessful){
                    SplashScreenActivity.editor.clear().apply()
                    getProfileAdmin(token, StaffReq(SplashScreenActivity.profileAdmin.result.tendangnhap))
                }
                else inforInterface.UpdateError()
            }
            override fun onFailure(call: Call<UserOrderRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getProfileAdmin(token : String, req: StaffReq){
        ApiStaffService.Api.api.authGetProfileStaff(token,req).enqueue(object : Callback<MainStaffRes>{
            override fun onResponse(call: Call<MainStaffRes>, response: Response<MainStaffRes>) {
                if(response.isSuccessful){
                    SplashScreenActivity.editor.putString("token",SplashScreenActivity.token)
                    println("mã quyền: "+SplashScreenActivity.roleId)
                    SplashScreenActivity.editor.putInt("roleId",SplashScreenActivity.roleId)
                    setProfileAdmin(response.body()!!)
                    inforInterface.UpdateSucces()
                }
            }

            override fun onFailure(call: Call<MainStaffRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun setProfileAdmin(response : MainStaffRes){
        var strResponse = gson.toJson(response).toString()
        SplashScreenActivity.editor.putString("profileAdmin",strResponse)
    }

    fun getProfileAdminEditor(){
        var strResponse =  SplashScreenActivity.sharedPreferences.getString("profileAdmin","")
        SplashScreenActivity.profileAdmin = gson.fromJson(strResponse, MainStaffRes::class.java)
    }

    fun handleString(s: String): String {
        var str = s
        str = str.trim()
        val arrWord = str.split(" ");
        str = ""
        for (word in arrWord) {
            var newWord = word.toLowerCase()
            if (newWord.length > 0) {
                   newWord = newWord.replaceFirst((newWord[0] + ""), (newWord[0] + "").toUpperCase())
                str += newWord + " "
            }
        }
        return str.trim()
    }
}