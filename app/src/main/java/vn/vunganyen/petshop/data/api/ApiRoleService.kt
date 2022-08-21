package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.admin.role.MainRoleRes
import vn.vunganyen.petshop.data.model.client.auth.login.LoginReq
import vn.vunganyen.petshop.data.model.client.register.findAuth.FindAuthRes
import vn.vunganyen.petshop.data.model.client.register.findRole.FindRoleReq
import vn.vunganyen.petshop.data.model.client.register.findRole.MainFindRole

interface ApiRoleService {

    @POST("v1/role/findId")
    fun findRole(@Body req: FindRoleReq):Call<MainFindRole>

    @GET("v1/role/list")
    fun getListRole(@Header("Authorization") BearerToken: String):Call<MainRoleRes>

    object Api {
        val api: ApiRoleService by lazy { RetrofitSetting().retrofit.create(ApiRoleService::class.java) }
    }

}