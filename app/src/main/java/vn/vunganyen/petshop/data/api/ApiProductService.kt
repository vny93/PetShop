package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.product.get.MainProductRes
import vn.vunganyen.petshop.data.model.product.get.ProductReq
import vn.vunganyen.petshop.data.model.product.search.SearchProductReq
import vn.vunganyen.petshop.data.model.product.userUpdateOrder.UserOrderReq
import vn.vunganyen.petshop.data.model.product.userUpdateOrder.UserOrderRes

interface ApiProductService {

    @POST("v1/product/list_fk")
    fun getListFK(@Body req: ProductReq):Call<MainProductRes>

    @GET("v1/product/list/discount")
    fun getListDiscount():Call<MainProductRes>

    @GET("v1/product/list/isnew")
    fun getListIsNew():Call<MainProductRes>

    @GET("v1/product/list/isgood")
    fun getListIsGood():Call<MainProductRes>

    @POST("v1/product/list/brand")
    fun getListByBrand(@Body req : BrandDetailReq):Call<MainProductRes>

    @POST("v1/product/list/search")
    fun searchProduct(@Body req: SearchProductReq):Call<MainProductRes>



    @PUT("v1/product/update/amount")
    fun userUpdateOrder(@Header("Authorization") BearerToken: String, @Body req: UserOrderReq): Call<UserOrderRes>
    //xử lý bằng cách lấy từ cái chi tiết giỏ hàng ra 1 list danh sách rồi call api update cái DS đó
    //hoặc là tạo ra 1 đối tượng gồm sl,masp xong lưu lại lúc call list chi tiết giỏ hàng ở bên kia rồi sd nó để update


    object Api {
        val api: ApiProductService by lazy { RetrofitSetting().retrofit.create(ApiProductService::class.java) }
    }

}