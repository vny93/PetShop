package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffReq

interface ApiStaffService {

    @POST("v1/staff/detail/tk")
    fun authGetProfileStaff(@Header("Authorization") BearerToken: String, @Body req: StaffReq): Call<MainStaffRes>

//    @POST("v1/user/register")
//    fun addProfile(@Body req: AddProfileReq): Call<MainAddProfile>
//
//    @POST("v1/user/findEmail")
//    fun findEmail(@Body req: FindEmailReq): Call<FindEmailRes>
//
//    @POST("v1/user/findPhone")
//    fun findPhone(@Body req: FindPhoneReq): Call<FindPhoneRes>
//
//    @PUT("v1/user/update")
//    fun updateProfile(@Header("Authorization") BearerToken: String, @Body req: UserRes): Call<PutCDRes>

    object Api {
        val api: ApiStaffService by lazy { RetrofitSetting().retrofit.create(ApiStaffService::class.java) }
    }
}