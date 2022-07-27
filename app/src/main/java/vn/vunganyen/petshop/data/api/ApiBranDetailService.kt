package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.brandDetail.MainBrandDetailRes
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailReq

interface ApiBranDetailService {

    @POST("v1/brand/detail")
    fun getBrandDetail(@Body req: BrandDetailReq):Call<MainBrandDetailRes>

//    @DELETE("v1/auth/logout")
//    fun authLogout(@Header("Authorization") BearerToken: String):Call<ComplaintResponse>


    object Api {
        val api: ApiBranDetailService by lazy { RetrofitSetting().retrofit.create(ApiBranDetailService::class.java) }
    }

}