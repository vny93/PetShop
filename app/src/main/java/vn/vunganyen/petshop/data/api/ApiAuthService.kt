package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.auth.changePw.ChangePwRes
import vn.vunganyen.petshop.data.model.auth.login.LoginReq
import vn.vunganyen.petshop.data.model.auth.login.LoginRes
import vn.vunganyen.petshop.data.model.register.addAuth.AddAuthReq
import vn.vunganyen.petshop.data.model.register.addAuth.MainAddAuth
import vn.vunganyen.petshop.data.model.register.findAuth.FindAuthReq
import vn.vunganyen.petshop.data.model.register.findAuth.FindAuthRes

interface ApiAuthService {

    @POST("v1/auth/login")
    fun authLogin(@Body login: LoginReq):Call<LoginRes>

    @POST("v1/auth/findAccount")
    fun findAuth(@Body req: FindAuthReq):Call<FindAuthRes>

    @POST("v1/auth/register")
    fun addAuth(@Body req: AddAuthReq):Call<MainAddAuth>

    @POST("v1/auth/check")
    fun checkAuth(@Header("Authorization") BearerToken: String, @Body req: LoginReq):Call<FindAuthRes>

    @PUT("v1/auth/update/password")
    fun changePassword(@Header("Authorization") BearerToken: String, @Body req: LoginReq):Call<ChangePwRes>


    object Api {
        val api: ApiAuthService by lazy { RetrofitSetting().retrofit.create(ApiAuthService::class.java) }
    }

}