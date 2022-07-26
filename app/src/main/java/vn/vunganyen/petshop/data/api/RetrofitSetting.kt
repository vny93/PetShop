package vn.vunganyen.petshop.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSetting {
        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(PathApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
}

