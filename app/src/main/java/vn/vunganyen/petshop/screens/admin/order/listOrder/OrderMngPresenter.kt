package vn.vunganyen.petshop.screens.admin.order.listOrder

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.model.admin.cart.getOrder.GetOrderReq
import vn.vunganyen.petshop.data.model.client.cart.add.MainAddCardRes
import vn.vunganyen.petshop.data.model.client.cart.getByStatus.MainCartStatusRes

class OrderMngPresenter {
    var orderMngInterface : OrderMngInterface

    constructor(orderMngInterface: OrderMngInterface) {
        this.orderMngInterface = orderMngInterface
    }

    fun filterCart(token: String, req: GetOrderReq){
        ApiCartService.Api.api.getOrder(token,req).enqueue(object : Callback<MainAddCardRes>{
            override fun onResponse(call: Call<MainAddCardRes>, response: Response<MainAddCardRes>) {
                if(response.isSuccessful){
                    orderMngInterface.getList(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainAddCardRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}