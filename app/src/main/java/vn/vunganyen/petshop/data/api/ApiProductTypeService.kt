package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.productType.MainProTypeRes

interface ApiProductTypeService {

    @GET("v1/productType/list")
    fun getList():Call<MainProTypeRes>

//    @DELETE("v1/auth/logout")
//    fun authLogout(@Header("Authorization") BearerToken: String):Call<ComplaintResponse>


    object Api {
        val api: ApiProductTypeService by lazy { RetrofitSetting().retrofit.create(ApiProductTypeService::class.java) }
    }

}