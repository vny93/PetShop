package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import vn.vunganyen.petshop.data.model.district.DistrictRes
import vn.vunganyen.petshop.data.model.district.MainGetDistrictRes

interface ApiDistrictServer {
    @GET("p/79?depth=2")
    fun getDistrict1(): Call<MainGetDistrictRes>

    @GET("d/{code}?depth=2")
    fun getDistrict2(@Path("code") code : Long): Call<DistrictRes>

    object Api {
        val api: ApiDistrictServer by lazy { RetrofitSetting4().retrofit.create(ApiDistrictServer::class.java) }
    }
}