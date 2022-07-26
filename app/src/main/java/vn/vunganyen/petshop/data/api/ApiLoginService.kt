package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.login.LoginReq
import vn.vunganyen.petshop.data.model.login.LoginRes

interface ApiLoginService {

    @POST("v1/auth/login")
    fun authLogin(@Body login: LoginReq):Call<LoginRes>

//    @DELETE("v1/auth/logout")
//    fun authLogout(@Header("Authorization") BearerToken: String):Call<ComplaintResponse>


    object Api {
        val api: ApiLoginService by lazy { RetrofitSetting().retrofit.create(ApiLoginService::class.java) }
    }

}