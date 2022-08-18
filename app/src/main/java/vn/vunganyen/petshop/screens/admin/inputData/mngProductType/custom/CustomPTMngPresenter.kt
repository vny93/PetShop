package vn.vunganyen.petshop.screens.admin.inputData.mngProductType.custom

import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiBrandService
import vn.vunganyen.petshop.data.api.ApiProductTypeService
import vn.vunganyen.petshop.data.api.RealPathUtil
import vn.vunganyen.petshop.data.model.admin.brand.uploadInfor.PostBrandRes
import vn.vunganyen.petshop.data.model.admin.productType.checkPTUse.CheckPTRes
import vn.vunganyen.petshop.data.model.admin.productType.insert.MainPTRes
import vn.vunganyen.petshop.data.model.admin.productType.updateInfor.ImagePTReq
import vn.vunganyen.petshop.data.model.admin.productType.updateInfor.InforPTReq
import vn.vunganyen.petshop.data.model.client.productType.ProductTypeRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.io.File


class CustomPTMngPresenter {
    var customPTMngInterface : CustomPTMngInterface

    constructor(customPTMngInterface: CustomPTMngInterface) {
        this.customPTMngInterface = customPTMngInterface
    }

    fun validCheck(id : String , name : String) {
        if (id.isEmpty() || name.isEmpty()) {
            customPTMngInterface.Empty()
            return
        }
        if(CustomPTMngActivity.type.equals("update")){
            updateInforPT(SplashScreenActivity.token, InforPTReq(id,name))
        }
        else {
            if (CustomPTMngActivity.mUri != null) {
                pathImage(CustomPTMngActivity.mUri!!, SplashScreenActivity.token, id, name)
            }
            else customPTMngInterface.ImageEmpty()
        }
    }

    fun insert(token: String, req : ProductTypeRes ){
        ApiProductTypeService.Api.api.insertProductType(token,req).enqueue(object : Callback<MainPTRes>{
            override fun onResponse(call: Call<MainPTRes>, response: Response<MainPTRes>) {
                if(response.isSuccessful){
                    customPTMngInterface.InsertSuccess()
                }
                else customPTMngInterface.InsertError()
            }

            override fun onFailure(call: Call<MainPTRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun pathImage(uri: Uri, token: String,id : String , name : String) {
        if (uri != null) {
            var strRealPath: String = RealPathUtil().getRealPath(CustomPTMngActivity.contextPTmng, uri)
            println("URL: " + strRealPath)
            var file = File(strRealPath)
            var request: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            var multipart = MultipartBody.Part.createFormData("image", file.name, request)
            getImageUrl(token, id, name, multipart) //call insert luôn
        }
    }

    fun getImageUrl(token: String,id : String , name : String, multipart : MultipartBody.Part){
        ApiBrandService.Api.api.getImageUrl(token, multipart).enqueue(object :
            Callback<PostBrandRes> {
            override fun onResponse(call: Call<PostBrandRes>, response: Response<PostBrandRes>) {
                if(response.isSuccessful){
                    println("ok nè: "+response.body()!!.result)
                    if(CustomPTMngActivity.type.equals("update")){
                        var req = ImagePTReq(id,response.body()!!.result)
                        updateImage(token,req)
                    }
                    else{
                        var req = ProductTypeRes(id,name,response.body()!!.result)
                        insert(token, req)
                    }
                }
            }
            override fun onFailure(call: Call<PostBrandRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateInforPT(token: String, req : InforPTReq){
        ApiProductTypeService.Api.api.updateInforPT(token,req).enqueue(object : Callback<CheckPTRes>{
            override fun onResponse(call: Call<CheckPTRes>, response: Response<CheckPTRes>) {
                if(response.isSuccessful){
                    if(CustomPTMngActivity.mUri != null){
                        pathImage(CustomPTMngActivity.mUri!!, SplashScreenActivity.token, req.maloaisp, req.tenloaisp)
                    }else customPTMngInterface.UpdateSuccess()
                }else customPTMngInterface.UpdateError()
            }

            override fun onFailure(call: Call<CheckPTRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateImage(token: String, req: ImagePTReq){
        ApiProductTypeService.Api.api.updateImagePT(token, req).enqueue(object : Callback<CheckPTRes>{
            override fun onResponse(call: Call<CheckPTRes>, response: Response<CheckPTRes>) {
                if(response.isSuccessful){
                    customPTMngInterface.UpdateSuccess()
                }
                else customPTMngInterface.UpdateError()
            }

            override fun onFailure(call: Call<CheckPTRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}