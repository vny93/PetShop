package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductReq
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductRes
import vn.vunganyen.petshop.data.model.admin.product.getList.MainProductOriginalRes
import vn.vunganyen.petshop.data.model.admin.staff.getDetail.PostDetailStaffReq
import vn.vunganyen.petshop.data.model.admin.staff.getList.MainStaffMngRes
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffReq
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffRes
import vn.vunganyen.petshop.data.model.admin.staff.updateProfile.PutStaffReq
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderRes
import vn.vunganyen.petshop.data.model.client.user.findEmail.FindEmailReq
import vn.vunganyen.petshop.data.model.client.user.findEmail.FindEmailRes
import vn.vunganyen.petshop.data.model.client.user.findPhone.FindPhoneReq
import vn.vunganyen.petshop.data.model.client.user.findPhone.FindPhoneRes

interface ApiStaffService {

    @POST("v1/staff/detail/tk")
    fun authGetProfileStaff(@Header("Authorization") BearerToken: String, @Body req: StaffReq): Call<MainStaffRes>

    @GET("v1/staff/list")
    fun getList(@Header("Authorization") BearerToken: String):Call<MainStaffMngRes>

    @POST("v1/staff/detail")
    fun getStaffDetail(@Header("Authorization") BearerToken: String, @Body req: PostDetailStaffReq): Call<MainStaffRes>

    @POST("v1/staff/register")
    fun insertStaff(@Header("Authorization") BearerToken: String, @Body req: StaffRes): Call<MainStaffRes>

    @PUT("v1/staff/update")
    fun updatetStaff(@Header("Authorization") BearerToken: String, @Body req: PutStaffReq): Call<UserOrderRes>

    @POST("v1/staff/check/use")
    fun checkStaffUse(@Header("Authorization") BearerToken: String,@Body req: PostDetailStaffReq): Call<CheckProductRes>

    @POST("v1/staff/delete")
    fun deleteStaff(@Header("Authorization") BearerToken: String,@Body req: PostDetailStaffReq): Call<CheckProductRes>

    @POST("v1/staff/findEmail")
    fun findEmail(@Header("Authorization") BearerToken: String,@Body req: FindEmailReq): Call<CheckProductRes>

    @POST("v1/staff/findPhone")
    fun findPhone(@Header("Authorization") BearerToken: String,@Body req: FindPhoneReq): Call<CheckProductRes>

    object Api {
        val api: ApiStaffService by lazy { RetrofitSetting().retrofit.create(ApiStaffService::class.java) }
    }
}