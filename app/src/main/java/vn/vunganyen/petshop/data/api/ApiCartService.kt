package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.brandDetail.MainBrandDetailRes
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.cart.add.CartReq
import vn.vunganyen.petshop.data.model.cart.add.MainCardRes
import vn.vunganyen.petshop.data.model.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.cart.getByStatus.MainCartStatusRes

interface ApiCartService {

    @POST("v1/cart/detail")
    fun getCartDetail(@Body req: BrandDetailReq):Call<MainBrandDetailRes>

    @POST("v1/cart/add")
    fun addCartDetail(@Header("Authorization") BearerToken: String, @Body req: CartReq): Call<MainCardRes>

    @POST("v1/cart/status")
    fun getCartByStatus(@Header("Authorization") BearerToken: String, @Body req: CartStatusReq): Call<MainCartStatusRes>



    object Api {
        val api: ApiCartService by lazy { RetrofitSetting().retrofit.create(ApiCartService::class.java) }
    }

}