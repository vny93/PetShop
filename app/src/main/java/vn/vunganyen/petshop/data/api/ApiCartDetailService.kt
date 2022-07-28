package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.cartDetail.getCartDetail.CartDetailReq
import vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail.MainListCartDetailRes
import vn.vunganyen.petshop.data.model.cartDetail.post.CartDetailReq2
import vn.vunganyen.petshop.data.model.cartDetail.post.MainCartDetailRes2

interface ApiCartDetailService {

    @POST("v1/cartDetail/add")
    fun addCartDetail(@Header("Authorization") BearerToken: String, @Body req: CartDetailReq2): Call<MainCartDetailRes2>

    @POST("v1/cartDetail/detail2")
    fun getListCartDetail(@Header("Authorization") BearerToken: String, @Body req: CartDetailReq): Call<MainListCartDetailRes>

    object Api {
        val api: ApiCartDetailService by lazy { RetrofitSetting().retrofit.create(ApiCartDetailService::class.java) }
    }

}