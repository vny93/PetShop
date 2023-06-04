package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.admin.cart.adminUpdate.AdminUpdateOrderReq
import vn.vunganyen.petshop.data.model.admin.cart.adminUpdate.UpdateStatusReq
import vn.vunganyen.petshop.data.model.admin.cart.getOrder.GetOrderReq
import vn.vunganyen.petshop.data.model.admin.cart.getOrderShipper.ShipperGetOrderReq
import vn.vunganyen.petshop.data.model.admin.cart.getTurnover.MainTurnoverRes
import vn.vunganyen.petshop.data.model.admin.cart.getTurnover.TurnoverReq
import vn.vunganyen.petshop.data.model.admin.cart.shipperUpdate.ShipperUpdateOrderReq
import vn.vunganyen.petshop.data.model.admin.cart.userUpdate.UserUpdateStatusReq
import vn.vunganyen.petshop.data.model.client.cart.add.AddCartReq
import vn.vunganyen.petshop.data.model.client.cart.add.MainAddCardRes
import vn.vunganyen.petshop.data.model.client.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.client.cart.getByStatus.MainCartStatusRes
import vn.vunganyen.petshop.data.model.client.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.client.cart.getCart.MainGetCartRes
import vn.vunganyen.petshop.data.model.client.cart.userUpdate.UserUpdateReq
import vn.vunganyen.petshop.data.model.client.cart.userUpdate.UserUpdateRes

interface ApiCartService {

    @POST("v1/cart/add")
    fun addCart(@Header("Authorization") BearerToken: String, @Body req: AddCartReq): Call<MainAddCardRes>

    @GET("v1/cart/list")
    fun getList(@Header("Authorization") BearerToken: String): Call<MainAddCardRes>

    @POST("v1/cart/status")
    fun getCartByStatus(@Header("Authorization") BearerToken: String, @Body req: CartStatusReq): Call<MainCartStatusRes>

    @POST("v1/cart/get/order")
    fun getOrder(@Header("Authorization") BearerToken: String, @Body req: GetOrderReq): Call<MainAddCardRes>

    @POST("v1/cart/get/order/shipper")
    fun getOrderShipper(@Header("Authorization") BearerToken: String, @Body req: ShipperGetOrderReq): Call<MainAddCardRes>

    @POST("v1/cart/get/turnover")
    fun getTurnover(@Header("Authorization") BearerToken: String, @Body req: TurnoverReq): Call<MainTurnoverRes>

    @PUT("v1/cart/update/user")
    fun userUpdateCard(@Header("Authorization") BearerToken: String, @Body req: UserUpdateReq): Call<UserUpdateRes>

    @PUT("v1/cart/update/admin")
    fun adminUpdateCard(@Header("Authorization") BearerToken: String, @Body req: AdminUpdateOrderReq): Call<UserUpdateRes>

    @PUT("v1/cart/update/admin/status")
    fun adminUpdateStatus(@Header("Authorization") BearerToken: String, @Body req: UpdateStatusReq): Call<UserUpdateRes>

    @PUT("v1/cart/update/shipper")
    fun shipperUpdateCard(@Header("Authorization") BearerToken: String, @Body req: ShipperUpdateOrderReq): Call<UserUpdateRes>

    @PUT("v1/cart/user/update/status")
    fun userUpdateStatus(@Header("Authorization") BearerToken: String, @Body req: UserUpdateStatusReq): Call<UserUpdateRes>

    @POST("v1/cart/detail")
    fun get(@Header("Authorization") BearerToken: String, @Body req: GetCartReq): Call<MainGetCartRes>

    object Api {
        val api: ApiCartService by lazy { RetrofitSetting().retrofit.create(ApiCartService::class.java) }
    }

}