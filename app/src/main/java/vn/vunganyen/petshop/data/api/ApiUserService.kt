package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import vn.vunganyen.petshop.data.model.client.cartDetail.update.PutCDRes
import vn.vunganyen.petshop.data.model.client.user.addProfile.AddProfileReq
import vn.vunganyen.petshop.data.model.client.user.addProfile.MainAddProfile
import vn.vunganyen.petshop.data.model.client.user.findEmail.FindEmailReq
import vn.vunganyen.petshop.data.model.client.user.findEmail.FindEmailRes
import vn.vunganyen.petshop.data.model.client.user.findPhone.FindPhoneReq
import vn.vunganyen.petshop.data.model.client.user.findPhone.FindPhoneRes
import vn.vunganyen.petshop.data.model.client.user.getProfile.MainUserRes
import vn.vunganyen.petshop.data.model.client.user.getProfile.UserReq
import vn.vunganyen.petshop.data.model.client.user.getProfile.UserRes

interface ApiUserService {
    @POST("v1/user/detail/tk")
    fun authGetProfile(@Header("Authorization") BearerToken: String, @Body req: UserReq): Call<MainUserRes>

    @POST("v1/user/register")
    fun addProfile(@Body req: AddProfileReq): Call<MainAddProfile>

    @POST("v1/user/findEmail")
    fun findEmail(@Body req: FindEmailReq): Call<FindEmailRes>

    @POST("v1/user/findPhone")
    fun findPhone(@Body req: FindPhoneReq): Call<FindPhoneRes>

    @PUT("v1/user/update")
    fun updateProfile(@Header("Authorization") BearerToken: String, @Body req: UserRes): Call<PutCDRes>

    object Api {
        val api: ApiUserService by lazy { RetrofitSetting().retrofit.create(ApiUserService::class.java) }
    }
}