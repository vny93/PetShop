package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import vn.vunganyen.petshop.data.model.user.MainUserRes
import vn.vunganyen.petshop.data.model.user.UserReq

interface ApiProfileService {
    @POST("v1/user/detail/tk")
    fun authGetProfile(@Header("Authorization") BearerToken: String, @Body req: UserReq): Call<MainUserRes>

    object Api {
        val api: ApiProfileService by lazy { RetrofitSetting().retrofit.create(ApiProfileService::class.java) }
    }
}