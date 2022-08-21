package vn.vunganyen.petshop.screens.admin.inputData.mngProduct.customProduct

import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.*
import vn.vunganyen.petshop.data.model.admin.brand.uploadInfor.PostBrandRes
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductRes
import vn.vunganyen.petshop.data.model.admin.product.getList.ProductOriginalRes
import vn.vunganyen.petshop.data.model.admin.product.insert.MainInsertPMngRes
import vn.vunganyen.petshop.data.model.admin.product.uploadImage.PutProductImageReq
import vn.vunganyen.petshop.data.model.admin.product.uploadInfor.PutProductInforReq
import vn.vunganyen.petshop.data.model.admin.productType.updateInfor.ImagePTReq
import vn.vunganyen.petshop.data.model.admin.provider.getlist.MainProviderRes
import vn.vunganyen.petshop.data.model.client.brand.MainBrandRes
import vn.vunganyen.petshop.data.model.client.proDetail.MainProDetailRes
import vn.vunganyen.petshop.data.model.client.proDetail.ProDetailReq
import vn.vunganyen.petshop.data.model.client.productType.MainProTypeRes
import vn.vunganyen.petshop.data.model.client.productType.ProductTypeRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.io.File

class CustomPMngPresenter {
    var customPMngInterface : CustomPMngInterface

    constructor(customPMngInterface: CustomPMngInterface) {
        this.customPMngInterface = customPMngInterface
    }

    fun getBrandName(){
        ApiBrandService.Api.api.getList().enqueue(object : Callback<MainBrandRes> {
            override fun onResponse(call: Call<MainBrandRes>, response: Response<MainBrandRes>) {
                if(response.isSuccessful){
                    customPMngInterface.GetListBrand(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainBrandRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getListPT(){
        ApiProductTypeService.Api.api.getList().enqueue(object : Callback<MainProTypeRes>{
            override fun onResponse(call: Call<MainProTypeRes>, response: Response<MainProTypeRes>){
                if(response.isSuccessful){
                    customPMngInterface.GetListPT(response.body()!!.result)
                }
            }
            override fun onFailure(call: Call<MainProTypeRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getListProvider(){
        ApiProviderService.Api.api.getlist(SplashScreenActivity.token).enqueue(object : Callback<MainProviderRes>{
            override fun onResponse(call: Call<MainProviderRes>,response: Response<MainProviderRes>) {
                if(response.isSuccessful){
                    customPMngInterface.GetListProvider(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainProviderRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun validCheck(req : PutProductInforReq) {
        if (req.masp.isEmpty() || req.tensp.isEmpty() || req.gia == -1.0F || req.soluong == -1
            || req.mota.isEmpty() || req.maloaisp.isEmpty() || req.mahang.isEmpty() || req.manhacc.isEmpty()) {
            customPMngInterface.Empty()
            return
        }
        if(req.gia == 0.0F){
            customPMngInterface.PriceError()
            return
        }
        if(req.soluong == 0){
            customPMngInterface.NumberError()
            return
        }
        if(CustomPMngActivity.typeProduct.equals("update")){
            updateInfor(SplashScreenActivity.token, req)
        }
        else {
            checkIdExist(SplashScreenActivity.token, req)
        }
    }

    fun checkIdExist(token : String , req : PutProductInforReq){
        ApiProDetailService.Api.api.getProDetail(ProDetailReq(req.masp)).enqueue(object : Callback<MainProDetailRes>{
            override fun onResponse(call: Call<MainProDetailRes>,response: Response<MainProDetailRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0){
                        customPMngInterface.IdExis()
                    }else {
                        if (CustomPMngActivity.mUri != null) {
                            pathImage(CustomPMngActivity.mUri!!, token, req)
                        }
                        else customPMngInterface.ImageEmpty()
                    }
                }
            }

            override fun onFailure(call: Call<MainProDetailRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun pathImage(uri: Uri, token: String, req: PutProductInforReq) {
        if (uri != null) {
            var strRealPath: String = RealPathUtil().getRealPath(CustomPMngActivity.contextPmng, uri)
            println("URL: " + strRealPath)
            var file = File(strRealPath)
            var request: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            var multipart = MultipartBody.Part.createFormData("image", file.name, request)
            getImageUrl(token, req, multipart) //call insert luôn
        }
    }

    fun getImageUrl(token: String, req : PutProductInforReq, multipart : MultipartBody.Part){
        ApiBrandService.Api.api.getImageUrl(token, multipart).enqueue(object :
            Callback<PostBrandRes> {
            override fun onResponse(call: Call<PostBrandRes>, response: Response<PostBrandRes>) {
                if(response.isSuccessful){
                    println("ok nè: "+response.body()!!.result)
                    if(CustomPMngActivity.typeProduct.equals("update")){
                        var reqImage = PutProductImageReq(req.masp,response.body()!!.result)
                        updateImage(token,reqImage)
                    }
                    else{
                        var reqProduct = ProductOriginalRes(req.masp,req.tensp,req.gia,
                            req.soluong,req.mota,response.body()!!.result,req.maloaisp,req.mahang,req.manhacc,
                            req.isgood, req.isnew)
                        insertProduct(token,reqProduct)
                    }
                }
            }
            override fun onFailure(call: Call<PostBrandRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun insertProduct(token : String, req : ProductOriginalRes){
        ApiProductService.Api.api.insertProduct(token,req).enqueue(object : Callback<MainInsertPMngRes>{
            override fun onResponse(call: Call<MainInsertPMngRes>, response: Response<MainInsertPMngRes>) {
                if(response.isSuccessful){
                    customPMngInterface.InsertSuccess()
                }
                else customPMngInterface.InsertError()
            }
            override fun onFailure(call: Call<MainInsertPMngRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun updateInfor(token : String , req : PutProductInforReq){
        ApiProductService.Api.api.updateInforProduct(token,req).enqueue(object : Callback<CheckProductRes>{
            override fun onResponse(call: Call<CheckProductRes>, response: Response<CheckProductRes>) {
                if(response.isSuccessful){
                    if(CustomPMngActivity.mUri != null){
                        pathImage(CustomPMngActivity.mUri!!, token, req)
                    }
                    else customPMngInterface.UpdateSuccess()
                }
                else customPMngInterface.UpdateError()
            }
            override fun onFailure(call: Call<CheckProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun updateImage(token : String , req: PutProductImageReq){
        ApiProductService.Api.api.updateImageProduct(token,req).enqueue(object : Callback<CheckProductRes>{
            override fun onResponse(call: Call<CheckProductRes>, response: Response<CheckProductRes>) {
                if(response.isSuccessful){
                    customPMngInterface.UpdateSuccess()
                }
                else customPMngInterface.UpdateError()
            }
            override fun onFailure(call: Call<CheckProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}

