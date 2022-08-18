package vn.vunganyen.petshop.screens.admin.inputData.mngBrand.insertBrand

import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiBrandService
import vn.vunganyen.petshop.data.api.RealPathUtil
import vn.vunganyen.petshop.data.model.admin.brand.uploadInfor.PostBrandRes
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.client.brandDetail.MainBrandDetailRes
import vn.vunganyen.petshop.data.model.client.user.findEmail.FindEmailReq
import vn.vunganyen.petshop.data.model.client.user.findPhone.FindPhoneReq
import vn.vunganyen.petshop.screens.admin.inputData.mngBrand.updateBrand.BrandDetailMngActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.io.File

class InsertBrandPresenter {
    var insertBrandInterface : InsertBrandInterface

    constructor(insertBrandInterface: InsertBrandInterface) {
        this.insertBrandInterface = insertBrandInterface
    }

    fun validCheck(req : BrandDetailRes){
        if (req.tenhang.isEmpty() || req.email.isEmpty() || req.sdt.isEmpty()){
            insertBrandInterface.Empty()
            return
        }
        if (!SplashScreenActivity.SDT.matcher(req.sdt).matches()) {
            insertBrandInterface.PhoneIllegal()
            return
        }
        if (!SplashScreenActivity.EMAIL_ADDRESS.matcher(req.email).matches()) {
            insertBrandInterface.EmailIllegal()
            return
        }
        checkPhoneExist(SplashScreenActivity.token,req)
    }

    fun checkPhoneExist(token: String, req: BrandDetailRes) {
        var request = FindPhoneReq(req.sdt)
        ApiBrandService.Api.api.findPhone(token, request).enqueue(object : Callback<PostBrandRes> {
            override fun onResponse(call: Call<PostBrandRes>, response: Response<PostBrandRes>) {
                if (response.isSuccessful) {
                    println("Phone đã tồn tại: " + response.body()!!.result)
                    insertBrandInterface.PhoneExist()
                } else checkEmailExist(token, req)
            }

            override fun onFailure(call: Call<PostBrandRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkEmailExist(token: String, req: BrandDetailRes) {
        var request = FindEmailReq(req.email)
        ApiBrandService.Api.api.findEmail(token, request).enqueue(object : Callback<PostBrandRes> {
            override fun onResponse(call: Call<PostBrandRes>, response: Response<PostBrandRes>) {
                if (response.isSuccessful) {
                    println("Email đã tồn tại: " + response.body()!!.result)
                    insertBrandInterface.EmailExist()
                    // }
                } else checkBrandId(token , req)
            }

            override fun onFailure(call: Call<PostBrandRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkBrandId(token : String , req: BrandDetailRes){
        ApiBrandService.Api.api.getBrandDetail(BrandDetailReq(req.mahang)).enqueue(object : Callback<MainBrandDetailRes>{
            override fun onResponse(call: Call<MainBrandDetailRes>,response: Response<MainBrandDetailRes>) {
                if(response.isSuccessful){
                    insertBrandInterface.BrandIdExist()
                }else {
                    if (BrandDetailMngActivity.mUri != null) {
                        getImageUrl(BrandDetailMngActivity.mUri!!, token, req)
                    } else insertBrandInterface.ImageEmpty()
                }

            }

            override fun onFailure(call: Call<MainBrandDetailRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getImageUrl(uri: Uri, token: String, req: BrandDetailRes) {
        if (uri != null) {
            var strRealPath: String = RealPathUtil().getRealPath(InsertBrandActivity.contextBrand, uri)
            println("URL: " + strRealPath)
            var file = File(strRealPath)
            var request: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            var multipart = MultipartBody.Part.createFormData("image", file.name, request)
            ApiBrandService.Api.api.getImageUrl(token, multipart).enqueue(object : Callback<PostBrandRes>{
                override fun onResponse(call: Call<PostBrandRes>,response: Response<PostBrandRes>) {
                    if(response.isSuccessful){
                        println("ok nè: "+response.body()!!.result)
                        var req2 = BrandDetailRes(req.mahang,req.tenhang,req.email,req.sdt,response.body()!!.result, req.mota)
                        insertBrand(token,req2)
                    }
                }

                override fun onFailure(call: Call<PostBrandRes>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    fun insertBrand(token : String, req : BrandDetailRes){
        println("logo: "+req.logo)
        ApiBrandService.Api.api.insertBrand(token,req).enqueue(object : Callback<MainBrandDetailRes>{
            override fun onResponse(call: Call<MainBrandDetailRes>,response: Response<MainBrandDetailRes>) {
                if(response.isSuccessful){
                    insertBrandInterface.InsertSuccess()
                }
            }

            override fun onFailure(call: Call<MainBrandDetailRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}