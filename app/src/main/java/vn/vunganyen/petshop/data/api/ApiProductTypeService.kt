package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.admin.productType.checkPTUse.CheckPTReq
import vn.vunganyen.petshop.data.model.admin.productType.checkPTUse.CheckPTRes
import vn.vunganyen.petshop.data.model.admin.productType.insert.MainPTRes
import vn.vunganyen.petshop.data.model.admin.productType.updateInfor.ImagePTReq
import vn.vunganyen.petshop.data.model.admin.productType.updateInfor.InforPTReq
import vn.vunganyen.petshop.data.model.client.productType.MainProTypeRes
import vn.vunganyen.petshop.data.model.client.productType.ProductTypeRes

interface ApiProductTypeService {

    @GET("v1/productType/list")
    fun getList():Call<MainProTypeRes>

    @POST("v1/productType/detail")
    fun getPTDetail(@Header("Authorization") BearerToken: String,@Body req: CheckPTReq): Call<MainPTRes>

    @POST("v1/productType/check/use")
    fun checkPTUse(@Header("Authorization") BearerToken: String,@Body req: CheckPTReq): Call<CheckPTRes>

    @POST("v1/productType/delete")
    fun removeProductType(@Header("Authorization") BearerToken: String,@Body req: CheckPTReq): Call<CheckPTRes>

    @PUT("v1/productType/update")
    fun updateProductType(@Header("Authorization") BearerToken: String,@Body req: ProductTypeRes): Call<CheckPTRes>

    @PUT("v1/productType/update/infor")
    fun updateInforPT(@Header("Authorization") BearerToken: String,@Body req: InforPTReq): Call<CheckPTRes>

    @PUT("v1/productType/update/image")
    fun updateImagePT(@Header("Authorization") BearerToken: String,@Body req: ImagePTReq): Call<CheckPTRes>

    @POST("v1/productType/add")
    fun insertProductType(@Header("Authorization") BearerToken: String,@Body req: ProductTypeRes): Call<MainPTRes>

    object Api {
        val api: ApiProductTypeService by lazy { RetrofitSetting().retrofit.create(ApiProductTypeService::class.java) }
    }

}