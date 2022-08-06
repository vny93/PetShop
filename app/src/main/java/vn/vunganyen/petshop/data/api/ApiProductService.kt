package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.cart.userUpdate.UserUpdateReq
import vn.vunganyen.petshop.data.model.cart.userUpdate.UserUpdateRes
import vn.vunganyen.petshop.data.model.product.get.MainProductRes
import vn.vunganyen.petshop.data.model.product.get.ProductReq
import vn.vunganyen.petshop.data.model.product.userUpdateOrder.UserOrderReq

interface ApiProductService {

    @POST("v1/product/list_fk")
    fun getListFK(@Body req: ProductReq):Call<MainProductRes>

    @PUT("v1/product/update/amount")
    fun userUpdateOrder(@Header("Authorization") BearerToken: String, @Body req: UserOrderReq): Call<UserUpdateRes>
    //xử lý bằng cách lấy từ cái chi tiết giỏ hàng ra 1 list danh sách rồi call api update cái DS đó
    //hoặc là tạo ra 1 đối tượng gồm sl,masp xong lưu lại lúc call list chi tiết giỏ hàng ở bên kia rồi sd nó để update


    object Api {
        val api: ApiProductService by lazy { RetrofitSetting().retrofit.create(ApiProductService::class.java) }
    }

}