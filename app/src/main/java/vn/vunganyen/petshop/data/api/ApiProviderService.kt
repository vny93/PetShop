package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.admin.provider.getlist.MainProviderRes
import vn.vunganyen.petshop.data.model.client.register.findRole.FindRoleReq
import vn.vunganyen.petshop.data.model.client.register.findRole.MainFindRole

interface ApiProviderService {

    @GET("v1/provider/list")
    fun getlist(@Header("Authorization") BearerToken: String):Call<MainProviderRes>


    object Api {
        val api: ApiProviderService by lazy { RetrofitSetting().retrofit.create(ApiProviderService::class.java) }
    }

}