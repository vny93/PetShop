package vn.vunganyen.petshop.screens.cart

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartDetailService
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.model.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.cart.getByStatus.MainCartStatusRes
import vn.vunganyen.petshop.data.model.cartDetail.getCartDetail.CartDetailReq
import vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail.MainListCartDetailRes

class CartPresenter {
    var cartInterface : CartInterface

    constructor(cartInterface: CartInterface) {
        this.cartInterface = cartInterface
    }

    fun getCartByStatus(token : String, req : CartStatusReq){
        ApiCartService.Api.api.getCartByStatus(token,req).enqueue(object :
            Callback<MainCartStatusRes> {
            override fun onResponse(call: Call<MainCartStatusRes>, response: Response<MainCartStatusRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0) {
                        println("magh: " + response.body()!!.result.get(0).magh)
                        var req2 = CartDetailReq(response.body()!!.result.get(0).magh)
                        getListCartDetail(token, req2)
                    }else{
                        cartInterface.cartEmpty()
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

    fun getListCartDetail(token: String, req: CartDetailReq){
        ApiCartDetailService.Api.api.getListCartDetail(token, req).enqueue(object : Callback<MainListCartDetailRes>{
            override fun onResponse(call: Call<MainListCartDetailRes>,response: Response<MainListCartDetailRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0) {
                        cartInterface.getListSuccess(response.body()!!.result)
                    }else{
                        cartInterface.cartEmpty()
                        println("giỏ hàng kh có sản phẩm") // hiện text view giỏ hàng trống
                    }
                }
            }

            override fun onFailure(call: Call<MainListCartDetailRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}