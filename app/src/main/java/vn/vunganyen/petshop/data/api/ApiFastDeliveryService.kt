package vn.vunganyen.petshop.data.api

import retrofit2.Call
import retrofit2.http.*
import vn.vunganyen.petshop.data.model.fastDelivery.order.SendDetailOrderReq
import vn.vunganyen.petshop.data.model.fastDelivery.order.SendOrderReq
import vn.vunganyen.petshop.data.model.fastDelivery.order.SendOrderResponse
import vn.vunganyen.petshop.data.model.fastDelivery.MainResponsePrice
import vn.vunganyen.petshop.data.model.fastDelivery.distance.RequestDistance
import vn.vunganyen.petshop.data.model.fastDelivery.RequestMass
import vn.vunganyen.petshop.data.model.fastDelivery.detailStatus.DetailStatusReq
import vn.vunganyen.petshop.data.model.fastDelivery.detailStatus.DetailStatusRes
import vn.vunganyen.petshop.data.model.fastDelivery.order.SendDetailOrderRes

interface ApiFastDeliveryService {

    @POST("v2/mass/get_price")
    fun getMassPrice(@Body req: RequestMass):Call<MainResponsePrice>

    @POST("v2/distance/get_price")
    fun getDistancePrice(@Body req: RequestDistance):Call<MainResponsePrice>

    @POST("v2/parcel/add")
    fun insertParcel(@Body req: SendOrderReq):Call<SendOrderResponse>

    @POST("v2/detailParcel/add")
    fun insertDetailParcel(@Body req: SendDetailOrderReq):Call<SendDetailOrderRes>

    @POST("v2/detailStatus/addStatus")
    fun insertStatus(@Body req: DetailStatusReq):Call<DetailStatusRes>


    object Api {
        val api: ApiFastDeliveryService by lazy { RetrofitSetting2().retrofit.create(ApiFastDeliveryService::class.java) }
    }

}