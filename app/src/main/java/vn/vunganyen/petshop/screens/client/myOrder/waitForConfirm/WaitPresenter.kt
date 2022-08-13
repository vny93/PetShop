package vn.vunganyen.petshop.screens.client.myOrder.waitForConfirm

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.model.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.cart.getByStatus.MainCartStatusRes

class WaitPresenter {
    var waitInterface : WaitInterface

    constructor(waitInterface: WaitInterface) {
        this.waitInterface = waitInterface
    }

    fun getCartByStatus(token : String, req : CartStatusReq){
        ApiCartService.Api.api.getCartByStatus(token,req).enqueue(object :
            Callback<MainCartStatusRes> {
            override fun onResponse(call: Call<MainCartStatusRes>, response: Response<MainCartStatusRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0) {
                        waitInterface.getListSuccess(response.body()!!.result)
                       // var req2 = GetCartReq(response.body()!!.result.get(0).magh)
                      //  getListCartDetail(token, req2)
                    }else{
                        waitInterface.getEmpty()
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