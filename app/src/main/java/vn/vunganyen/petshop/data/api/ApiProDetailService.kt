package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.brandDetail.MainBrandDetailRes
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.proDetail.MainProDetailRes
import vn.vunganyen.petshop.data.model.proDetail.ProDetailReq

interface ApiProDetailService {

    @POST("v1/product/detail")
    fun getProDetail(@Body req: ProDetailReq):Call<MainProDetailRes>

//    @DELETE("v1/auth/logout")
//    fun authLogout(@Header("Authorization") BearerToken: String):Call<ComplaintResponse>


    object Api {
        val api: ApiProDetailService by lazy { RetrofitSetting().retrofit.create(ApiProDetailService::class.java) }
    }

}