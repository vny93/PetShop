package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.brand.MainBrandRes
import vn.vunganyen.petshop.data.model.brandDetail.MainBrandDetailRes
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailReq

interface ApiBrandDetailService {

    @POST("v1/brand/detail")
    fun getBrandDetail(@Body req: BrandDetailReq):Call<MainBrandDetailRes>

    @GET("v1/brand/list")
    fun getList():Call<MainBrandRes>

//    @DELETE("v1/auth/logout")
//    fun authLogout(@Header("Authorization") BearerToken: String):Call<ComplaintResponse>


    object Api {
        val api: ApiBrandDetailService by lazy { RetrofitSetting().retrofit.create(ApiBrandDetailService::class.java) }
    }

}