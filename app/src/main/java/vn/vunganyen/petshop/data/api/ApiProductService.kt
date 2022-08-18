package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductReq
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductRes
import vn.vunganyen.petshop.data.model.admin.product.getList.MainProductOriginalRes
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.client.product.get.MainProductRes
import vn.vunganyen.petshop.data.model.client.product.get.ProductReq
import vn.vunganyen.petshop.data.model.client.product.search.SearchProductReq
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderReq
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderRes

interface ApiProductService {

    @POST("v1/product/list_fk")
    fun getListFK(@Body req: ProductReq):Call<MainProductRes>

    @GET("v1/product/list")
    fun getList(@Header("Authorization") BearerToken: String):Call<MainProductOriginalRes>

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

    @POST("v1/product/check/use")
    fun checkProductUse(@Header("Authorization") BearerToken: String,@Body req: CheckProductReq): Call<CheckProductRes>

    @POST("v1/product/delete")
    fun removeProduct(@Header("Authorization") BearerToken: String,@Body req: CheckProductReq): Call<CheckProductRes>



    @PUT("v1/product/update/amount")
    fun userUpdateOrder(@Header("Authorization") BearerToken: String, @Body req: UserOrderReq): Call<UserOrderRes>
    //xử lý bằng cách lấy từ cái chi tiết giỏ hàng ra 1 list danh sách rồi call api update cái DS đó
    //hoặc là tạo ra 1 đối tượng gồm sl,masp xong lưu lại lúc call list chi tiết giỏ hàng ở bên kia rồi sd nó để update


    object Api {
        val api: ApiProductService by lazy { RetrofitSetting().retrofit.create(ApiProductService::class.java) }
    }

}