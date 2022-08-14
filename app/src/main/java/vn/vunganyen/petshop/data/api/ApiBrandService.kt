package vn.vunganyen.petshop.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.admin.brand.uploadInfor.PostBrandReq
import vn.vunganyen.petshop.data.model.admin.brand.uploadInfor.PostBrandRes
import vn.vunganyen.petshop.data.model.admin.brand.uploadLogo.PutLogoBrandReq
import vn.vunganyen.petshop.data.model.client.brand.MainBrandRes
import vn.vunganyen.petshop.data.model.client.brandDetail.MainBrandDetailRes
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.client.user.findEmail.FindEmailReq
import vn.vunganyen.petshop.data.model.client.user.findPhone.FindPhoneReq

interface ApiBrandService {

    @POST("v1/brand/detail")
    fun getBrandDetail(@Body req: BrandDetailReq):Call<MainBrandDetailRes>

    @GET("v1/brand/list")
    fun getList():Call<MainBrandRes>

    @PUT("v1/brand/updateInfor")
    fun updateInforBrand(@Header("Authorization") BearerToken: String,@Body req: PostBrandReq):Call<PostBrandRes>

    @Multipart
    @PUT("v1/brand/update/image")//,@Part file : MultipartBody.Part
    fun updateLogoBrand(@Header("Authorization") BearerToken: String, @Part("mahang") mahang: RequestBody,@Part logo : MultipartBody.Part):Call<PostBrandRes>

    @POST("v1/brand/add")
    fun addBrand(@Header("Authorization") BearerToken: String,@Body req: BrandDetailRes):Call<MainBrandDetailRes>

    @POST("v1/brand/findEmail")
    fun findEmail(@Header("Authorization") BearerToken: String,@Body req: FindEmailReq): Call<PostBrandRes>

    @POST("v1/brand/findPhone")
    fun findPhone(@Header("Authorization") BearerToken: String,@Body req: FindPhoneReq): Call<PostBrandRes>


//    @DELETE("v1/auth/logout")
//    fun authLogout(@Header("Authorization") BearerToken: String):Call<ComplaintResponse>


    object Api {
        val api: ApiBrandService by lazy { RetrofitSetting().retrofit.create(ApiBrandService::class.java) }
    }

}