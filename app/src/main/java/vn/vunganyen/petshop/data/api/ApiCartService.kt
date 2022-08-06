package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.cart.add.AddCartReq
import vn.vunganyen.petshop.data.model.cart.add.MainAddCardRes
import vn.vunganyen.petshop.data.model.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.cart.getByStatus.MainCartStatusRes
import vn.vunganyen.petshop.data.model.cart.userUpdate.UserUpdateReq
import vn.vunganyen.petshop.data.model.cart.userUpdate.UserUpdateRes

interface ApiCartService {

//    @POST("v1/cart/detail")
//    fun getCartBrand(@Body req: BrandDetailReq):Call<MainBrandDetailRes>

    @POST("v1/cart/add")
    fun addCart(@Header("Authorization") BearerToken: String, @Body req: AddCartReq): Call<MainAddCardRes>

    @POST("v1/cart/status")
    fun getCartByStatus(@Header("Authorization") BearerToken: String, @Body req: CartStatusReq): Call<MainCartStatusRes>

    @PUT("v1/cart/update/user")
    fun userUpdateCard(@Header("Authorization") BearerToken: String, @Body req: UserUpdateReq): Call<UserUpdateRes>

    object Api {
        val api: ApiCartService by lazy { RetrofitSetting().retrofit.create(ApiCartService::class.java) }
    }

}