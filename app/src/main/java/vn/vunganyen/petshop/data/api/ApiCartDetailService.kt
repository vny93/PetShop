package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail.GetMainCDRes
import vn.vunganyen.petshop.data.model.cartDetail.post.PostCDReq
import vn.vunganyen.petshop.data.model.cartDetail.post.MainPostCDRes
import vn.vunganyen.petshop.data.model.cartDetail.update.PutCDReq
import vn.vunganyen.petshop.data.model.cartDetail.update.PutCDRes

interface ApiCartDetailService {

    @POST("v1/cartDetail/add")
    fun addCartDetail(@Header("Authorization") BearerToken: String, @Body req: PostCDReq): Call<MainPostCDRes>

    @POST("v1/cartDetail/detail2")
    fun getListCartDetail(@Header("Authorization") BearerToken: String, @Body req: GetCartReq): Call<GetMainCDRes>

    @PUT("v1/cartDetail/update")
    fun updateCartDetail(@Header("Authorization") BearerToken: String, @Body req: PutCDReq): Call<PutCDRes>

    object Api {
        val api: ApiCartDetailService by lazy { RetrofitSetting().retrofit.create(ApiCartDetailService::class.java) }
    }

}