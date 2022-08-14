package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.client.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.client.cartDetail.deleteCD.DeleteCDReq
import vn.vunganyen.petshop.data.model.client.cartDetail.deleteCD.DeleteCDRes
import vn.vunganyen.petshop.data.model.client.cartDetail.findCD.FindCDReq
import vn.vunganyen.petshop.data.model.client.cartDetail.findCD.MainFindDCRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetMainCDRes
import vn.vunganyen.petshop.data.model.client.cartDetail.post.PostCDReq
import vn.vunganyen.petshop.data.model.client.cartDetail.post.MainPostCDRes
import vn.vunganyen.petshop.data.model.client.cartDetail.update.PutCDReq
import vn.vunganyen.petshop.data.model.client.cartDetail.update.PutCDRes

interface ApiCartDetailService {

    @POST("v1/cartDetail/add")
    fun addCartDetail(@Header("Authorization") BearerToken: String, @Body req: PostCDReq): Call<MainPostCDRes>

    @POST("v1/cartDetail/detail2")
    fun getListCartDetail(@Header("Authorization") BearerToken: String, @Body req: GetCartReq): Call<GetMainCDRes>

    @POST("v1/cartDetail/find")
    fun findCartDetail(@Header("Authorization") BearerToken: String, @Body req: FindCDReq): Call<MainFindDCRes>

    @PUT("v1/cartDetail/update")
    fun updateCartDetail(@Header("Authorization") BearerToken: String, @Body req: PutCDReq): Call<PutCDRes>

    @POST("v1/cartDetail/delete/product")
    fun removeCartDetail(@Header("Authorization") BearerToken: String, @Body req: DeleteCDReq): Call<DeleteCDRes>

    object Api {
        val api: ApiCartDetailService by lazy { RetrofitSetting().retrofit.create(ApiCartDetailService::class.java) }
    }

}