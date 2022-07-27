package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.cartDetail.post.CartDetailReq2
import vn.vunganyen.petshop.data.model.cartDetail.post.MainCartDetailRes2

interface ApiCartDetailService {

    @POST("v1/cartDetail/add")
    fun addCartDetail(@Header("Authorization") BearerToken: String, @Body req: CartDetailReq2): Call<MainCartDetailRes2>

    object Api {
        val api: ApiCartDetailService by lazy { RetrofitSetting().retrofit.create(ApiCartDetailService::class.java) }
    }

}