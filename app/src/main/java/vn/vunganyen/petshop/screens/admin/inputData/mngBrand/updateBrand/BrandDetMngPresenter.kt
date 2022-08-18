package vn.vunganyen.petshop.screens.admin.inputData.mngBrand.updateBrand

import android.net.Uri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiBrandService
import vn.vunganyen.petshop.data.api.RealPathUtil
import vn.vunganyen.petshop.data.model.admin.brand.uploadInfor.PostBrandReq
import vn.vunganyen.petshop.data.model.admin.brand.uploadInfor.PostBrandRes
import vn.vunganyen.petshop.data.model.client.user.findEmail.FindEmailReq
import vn.vunganyen.petshop.data.model.client.user.findPhone.FindPhoneReq
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.io.File

class BrandDetMngPresenter {
    var brandDetMngInterface: BrandDetMngInterface

    constructor(brandDetMngInterface: BrandDetMngInterface) {
        this.brandDetMngInterface = brandDetMngInterface
    }

    fun validCheck(req: PostBrandReq) {
        if (req.tenhang.isEmpty() || req.email.isEmpty() || req.sdt.isEmpty()) {
            brandDetMngInterface.Empty()
            return
        }
        if (!SplashScreenActivity.SDT.matcher(req.sdt).matches()) {
            brandDetMngInterface.PhoneIllegal()
            return
        }
        if (!SplashScreenActivity.EMAIL_ADDRESS.matcher(req.email).matches()) {
            brandDetMngInterface.EmailIllegal()
            return
        }
        checkPhoneExist(SplashScreenActivity.token, req)
    }

    fun checkPhoneExist(token: String, req: PostBrandReq) {
        var request = FindPhoneReq(req.sdt)
        ApiBrandService.Api.api.findPhone(token, request).enqueue(object : Callback<PostBrandRes> {
            override fun onResponse(call: Call<PostBrandRes>, response: Response<PostBrandRes>) {
                if (response.isSuccessful) {
                    println("Phone đã tồn tại: " + response.body()!!.result)
//                    if(SplashScreenActivity.token.equals("")){//token = "" thì sao call api đc ?? mau thuẫn
//                        brandDetMngInterface.PhoneExist()
//                    }
//                    else{//tồn tại nhưng của brand hiện tại nên vẫn đúng
                    if (response.body()!!.result == BrandDetailMngActivity.brand.mahang) {
                        checkEmailExist(token, req)
                    } else brandDetMngInterface.PhoneExist()

                    //  }
                } else checkEmailExist(token, req)
            }

            override fun onFailure(call: Call<PostBrandRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkEmailExist(token: String, req: PostBrandReq) {
        var request = FindEmailReq(req.email)
        ApiBrandService.Api.api.findEmail(token, request).enqueue(object : Callback<PostBrandRes> {
            override fun onResponse(call: Call<PostBrandRes>, response: Response<PostBrandRes>) {
                if (response.isSuccessful) {
                    println("Email đã tồn tại: " + response.body()!!.result)
//                    if (SplashScreenActivity.token.equals("")) { // token = "" thì sao call api này đc?? mau thuẫn
//                        brandDetMngInterface.EmailExist()
//                    } else {//tồn tại nhưng của brand hiện tại nên vẫn đúng
                        if (response.body()!!.result == BrandDetailMngActivity.brand.mahang) {
                            updateInforBrand(token, req)
                        } else brandDetMngInterface.EmailExist()
                   // }
                } else updateInforBrand(token, req)
            }

            override fun onFailure(call: Call<PostBrandRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateInforBrand(token: String, req: PostBrandReq) {
        ApiBrandService.Api.api.updateInforBrand(token, req)
            .enqueue(object : Callback<PostBrandRes> {
                override fun onResponse(
                    call: Call<PostBrandRes>,
                    response: Response<PostBrandRes>
                ) {
                    if (response.isSuccessful) {
                        //getProfile(token)
                        if (BrandDetailMngActivity.mUri != null) {
                            checkUri(BrandDetailMngActivity.mUri!!, token, req)
                        } else brandDetMngInterface.UpdateSuccess()
                    } else brandDetMngInterface.UpdateError()
                }

                override fun onFailure(call: Call<PostBrandRes>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun checkUri(uri: Uri, token: String, req: PostBrandReq) {
        if (uri != null) {
            var strRealPath: String =
                RealPathUtil().getRealPath(BrandDetailMngActivity.context, uri)
            println("URL: " + strRealPath)
            var file = File(strRealPath)
            var request: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            var multipart = MultipartBody.Part.createFormData("logo", file.name, request)
            val id: RequestBody = req.mahang.toRequestBody("text/plain".toMediaType())
            ApiBrandService.Api.api.updateLogoBrand(token, id, multipart)
                .enqueue(object : Callback<PostBrandRes> {
                    override fun onResponse(
                        call: Call<PostBrandRes>,
                        response: Response<PostBrandRes>
                    ) {
                        if (response.isSuccessful) {
                            brandDetMngInterface.UpdateSuccess()
                        }
                    }

                    override fun onFailure(call: Call<PostBrandRes>, t: Throwable) {
                        brandDetMngInterface.UpdateError()
                    }
                })
        }
    }
}