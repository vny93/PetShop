package vn.vunganyen.petshop.screens.client.myOrder.transport

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.model.client.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.client.cart.getByStatus.MainCartStatusRes

class TransportPresenter {
    var transportInterface : TransportInterface

    constructor(transportInterface: TransportInterface) {
        this.transportInterface = transportInterface
    }

    fun getCartByStatus(token : String, req : CartStatusReq){
        ApiCartService.Api.api.getCartByStatus(token,req).enqueue(object :
            Callback<MainCartStatusRes> {
            override fun onResponse(call: Call<MainCartStatusRes>, response: Response<MainCartStatusRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0) {
                        transportInterface.getListSuccess(response.body()!!.result)
                        // var req2 = GetCartReq(response.body()!!.result.get(0).magh)
                        //  getListCartDetail(token, req2)
                    }else{
                        transportInterface.getEmpty()
                        println("chưa tạo giỏ hàng") // hiện text view giỏ hàng trống
                    }
                }
            }

            override fun onFailure(call: Call<MainCartStatusRes>, t: Throwable) {
                Log.d("error" , ""+call)
                println("error "+call)
                t.printStackTrace()
            }

        })
    }
}