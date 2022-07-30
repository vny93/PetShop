package vn.vunganyen.petshop.screens.register.newProfile

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiUserService
import vn.vunganyen.petshop.data.model.user.addProfile.AddProfileReq
import vn.vunganyen.petshop.data.model.user.addProfile.MainAddProfile
import vn.vunganyen.petshop.data.model.user.findEmail.FindEmailReq
import vn.vunganyen.petshop.data.model.user.findEmail.FindEmailRes
import vn.vunganyen.petshop.data.model.user.findPhone.FindPhoneReq
import vn.vunganyen.petshop.data.model.user.findPhone.FindPhoneRes
import vn.vunganyen.petshop.screens.home.HomeActivity

class ProfilePresenter {
    var profileInterface: ProfileInterface

    constructor(profileInterface: ProfileInterface) {
        this.profileInterface = profileInterface
    }

    fun validCheck(req: AddProfileReq) {
        if (req.hoten.isEmpty() || req.ngaysinh.isEmpty() || req.gioitinh.isEmpty() || req.diachi.isEmpty() ||
            req.email.isEmpty() || req.sdt.isEmpty() || req.masothue.isEmpty()){
            profileInterface.Empty()
            return
        }
        if (!HomeActivity.SDT.matcher(req.sdt).matches()) {
            profileInterface.PhoneIllegal()
            return
        }
        if (!HomeActivity.EMAIL_ADDRESS.matcher(req.email).matches()) {
            profileInterface.EmailIllegal()
            return
        }
        checkPhoneExist(req)
    }


    fun checkPhoneExist(req: AddProfileReq){
        var request = FindPhoneReq(req.sdt)
        ApiUserService.Api.api.findPhone(request).enqueue(object : Callback<FindPhoneRes>{
            override fun onResponse(call: Call<FindPhoneRes>, response: Response<FindPhoneRes>) {
                if(response.isSuccessful){
                    println("Phone đã tồn tại")
                    profileInterface.PhoneExist()
                }
                else checkEmailExist(req)
            }

            override fun onFailure(call: Call<FindPhoneRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkEmailExist(req: AddProfileReq){
        var request = FindEmailReq(req.email)
        ApiUserService.Api.api.findEmail(request).enqueue(object : Callback<FindEmailRes>{
            override fun onResponse(call: Call<FindEmailRes>, response: Response<FindEmailRes>) {
                if(response.isSuccessful){
                    println("đã tồn tại email")
                    profileInterface.EmailExist()
                }
                else addProfile(req)

            }

            override fun onFailure(call: Call<FindEmailRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addProfile(req : AddProfileReq){
        ApiUserService.Api.api.addProfile(req).enqueue(object : Callback<MainAddProfile>{
            override fun onResponse(call: Call<MainAddProfile>,response: Response<MainAddProfile>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        println("Thêm profile thành công")
                        profileInterface.AddSucces()
                    }
                    else{
                        println("Thêm profile thất bại")
                    }
                }
            }

            override fun onFailure(call: Call<MainAddProfile>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}