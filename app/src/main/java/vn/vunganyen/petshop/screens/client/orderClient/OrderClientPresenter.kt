package vn.vunganyen.petshop.screens.client.orderClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.model.admin.cart.getOrder.GetOrderReq
import vn.vunganyen.petshop.data.model.client.cart.add.MainAddCardRes

class OrderClientPresenter {
    var orderClientInterface : OrderClientInterface

    constructor(orderClientInterface: OrderClientInterface) {
        this.orderClientInterface = orderClientInterface
    }

    fun filterCart(token: String, req: GetOrderReq){
        ApiCartService.Api.api.getOrder(token,req).enqueue(object : Callback<MainAddCardRes> {
            override fun onResponse(call: Call<MainAddCardRes>, response: Response<MainAddCardRes>) {
                if(response.isSuccessful){
                    orderClientInterface.getList(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainAddCardRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}