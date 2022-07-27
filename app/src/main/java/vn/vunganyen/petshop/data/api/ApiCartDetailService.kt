package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.brandDetail.MainBrandDetailRes
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailReq

interface ApiCartDetailService {

    @POST("v1/cart/detail")
    fun getCartDetail(@Body req: BrandDetailReq):Call<MainBrandDetailRes>

//    @DELETE("v1/auth/logout")
//    fun authLogout(@Header("Authorization") BearerToken: String):Call<ComplaintResponse>


    object Api {
        val api: ApiCartDetailService by lazy { RetrofitSetting().retrofit.create(ApiCartDetailService::class.java) }
    }

}