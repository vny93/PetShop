package vn.vunganyen.petshop.screens.admin.orderShip.shipperListOrder

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.model.admin.cart.getOrderShipper.ShipperGetOrderReq
import vn.vunganyen.petshop.data.model.client.cart.add.MainAddCardRes

class ShipperOrderPresenter {
    var shipperOrderInterface : ShipperOrderInterface

    constructor(shipperOrderInterface: ShipperOrderInterface) {
        this.shipperOrderInterface = shipperOrderInterface
    }

    fun filterOrderShipper(token : String, req: ShipperGetOrderReq){
        ApiCartService.Api.api.getOrderShipper(token, req).enqueue(object : Callback<MainAddCardRes>{
            override fun onResponse(call: Call<MainAddCardRes>, response: Response<MainAddCardRes>) {
                if(response.isSuccessful){
                    shipperOrderInterface.getList(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainAddCardRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}