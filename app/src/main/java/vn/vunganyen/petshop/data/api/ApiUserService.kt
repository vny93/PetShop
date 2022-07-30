package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import vn.vunganyen.petshop.data.model.user.addProfile.AddProfileReq
import vn.vunganyen.petshop.data.model.user.addProfile.MainAddProfile
import vn.vunganyen.petshop.data.model.user.findEmail.FindEmailReq
import vn.vunganyen.petshop.data.model.user.findEmail.FindEmailRes
import vn.vunganyen.petshop.data.model.user.findPhone.FindPhoneReq
import vn.vunganyen.petshop.data.model.user.findPhone.FindPhoneRes
import vn.vunganyen.petshop.data.model.user.getProfile.MainUserRes
import vn.vunganyen.petshop.data.model.user.getProfile.UserReq

interface ApiUserService {
    @POST("v1/user/detail/tk")
    fun authGetProfile(@Header("Authorization") BearerToken: String, @Body req: UserReq): Call<MainUserRes>

    @POST("v1/user/register")
    fun addProfile(@Body req: AddProfileReq): Call<MainAddProfile>

    @POST("v1/user/findEmail")
    fun findEmail(@Body req: FindEmailReq): Call<FindEmailRes>

    @POST("v1/user/findPhone")
    fun findPhone(@Body req: FindPhoneReq): Call<FindPhoneRes>

    object Api {
        val api: ApiUserService by lazy { RetrofitSetting().retrofit.create(ApiUserService::class.java) }
    }
}