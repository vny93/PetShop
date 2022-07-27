package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.login.LoginReq
import vn.vunganyen.petshop.data.model.login.LoginRes
import vn.vunganyen.petshop.data.model.product.MainProductRes
import vn.vunganyen.petshop.data.model.product.ProductReq
import vn.vunganyen.petshop.data.model.productType.MainProTypeRes

interface ApiProductService {

    @POST("v1/product/list_fk")
    fun getListFK(@Body req: ProductReq):Call<MainProductRes>

//    @DELETE("v1/auth/logout")
//    fun authLogout(@Header("Authorization") BearerToken: String):Call<ComplaintResponse>


    object Api {
        val api: ApiProductService by lazy { RetrofitSetting().retrofit.create(ApiProductService::class.java) }
    }

}