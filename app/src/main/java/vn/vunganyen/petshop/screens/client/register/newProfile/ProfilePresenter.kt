package vn.vunganyen.petshop.screens.client.register.newProfile

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiUserService
import vn.vunganyen.petshop.data.model.cartDetail.update.PutCDRes
import vn.vunganyen.petshop.data.model.user.addProfile.AddProfileReq
import vn.vunganyen.petshop.data.model.user.addProfile.MainAddProfile
import vn.vunganyen.petshop.data.model.user.findEmail.FindEmailReq
import vn.vunganyen.petshop.data.model.user.findEmail.FindEmailRes
import vn.vunganyen.petshop.data.model.user.findPhone.FindPhoneReq
import vn.vunganyen.petshop.data.model.user.findPhone.FindPhoneRes
import vn.vunganyen.petshop.data.model.user.getProfile.MainUserRes
import vn.vunganyen.petshop.data.model.user.getProfile.UserReq
import vn.vunganyen.petshop.data.model.user.getProfile.UserRes
import vn.vunganyen.petshop.screens.client.home.main.HomeActivity

class ProfilePresenter {
    var profileInterface: ProfileInterface
    var gson = Gson()

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
                    if(HomeActivity.token.equals("")){
                        profileInterface.PhoneExist()
                    }
                    else{//tồn tại nhưng của user hiện tại nên vẫn đúng
                        if(response.body()!!.result == HomeActivity.profile.result.makh){
                            checkEmailExist(req)
                        }
                        else profileInterface.PhoneExist()
                    }
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
                    if(HomeActivity.token.equals("")){
                        profileInterface.EmailExist()
                    }
                    else{//tồn tại nhưng là của kh hiện tại thì vẫn được
                        if(response.body()!!.result == HomeActivity.profile.result.makh){
                            var id = HomeActivity.profile.result.makh
                            var request = UserRes(id,req.hoten,req.gioitinh,req.diachi,req.ngaysinh,req.sdt,req.email,req.masothue,req.tendangnhap)
                            updateProfile(HomeActivity.token,request)
                        }
                        else profileInterface.EmailExist()
                    }
                }

                else{
                    if(HomeActivity.token.equals("")){
                        addProfile(req)
                    }
                    else{
                        var id = HomeActivity.profile.result.makh
                        var request = UserRes(id,req.hoten,req.gioitinh,req.diachi,req.ngaysinh,req.sdt,req.email,req.masothue,req.tendangnhap)
                        updateProfile(HomeActivity.token,request)
                    }
                }
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

    fun updateProfile(token : String, req : UserRes){
        ApiUserService.Api.api.updateProfile(token,req).enqueue(object : Callback<PutCDRes>{
            override fun onResponse(call: Call<PutCDRes>, response: Response<PutCDRes>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        println("Cập nhật profile thành công")
                        HomeActivity.editor.clear().apply() // cập nhật lại editor
                       // profileInterface.UpdateSucces()
                        getProfile(HomeActivity.token, UserReq(req.tendangnhap))
                    }
                    else{
                        println("Cập nhật profile thất bại")
                    }
                }
            }

            override fun onFailure(call: Call<PutCDRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getProfile(token : String, req: UserReq){
        ApiUserService.Api.api.authGetProfile(token, req).enqueue(object : Callback<MainUserRes>{
            override fun onResponse(call: Call<MainUserRes>, response: Response<MainUserRes>) {
                if(response.isSuccessful){
                    println("mã khách hàng login: "+response.body()!!.result.makh)
                    //Lưu lại token với profile mới vì mới xóa editor lưu profile cũ
                    HomeActivity.editor.putString("token", HomeActivity.token)
                    setProfile(response.body()!!)
                    profileInterface.UpdateSucces()
                }
                else{
                    profileInterface.UpdateError()
                }
            }
            override fun onFailure(call: Call<MainUserRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun setProfile(response : MainUserRes){
        var strResponse = gson.toJson(response).toString()
        HomeActivity.editor.putString("profile",strResponse)
    }

    fun getProfileEditor(){
        var strResponse =  HomeActivity.sharedPreferences.getString("profile","")
        HomeActivity.profile = gson.fromJson(strResponse, MainUserRes::class.java)
    }
}