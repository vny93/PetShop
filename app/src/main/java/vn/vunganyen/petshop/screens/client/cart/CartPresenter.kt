package vn.vunganyen.petshop.screens.client.cart

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartDetailService
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.model.client.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.client.cart.getByStatus.MainCartStatusRes
import vn.vunganyen.petshop.data.model.client.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.client.cartDetail.deleteCD.DeleteCDReq
import vn.vunganyen.petshop.data.model.client.cartDetail.deleteCD.DeleteCDRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetMainCDRes

class CartPresenter {
    var cartInterface : CartInterface

    constructor(cartInterface: CartInterface) {
        this.cartInterface = cartInterface
    }

    fun getCartByStatus(token : String, req : CartStatusReq){
        println("makh: "+req.makh)
        ApiCartService.Api.api.getCartByStatus(token,req).enqueue(object :
            Callback<MainCartStatusRes> {
            override fun onResponse(call: Call<MainCartStatusRes>, response: Response<MainCartStatusRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0) {
                        println("magh: " + response.body()!!.result.get(0).magh)
                        var req2 = GetCartReq(response.body()!!.result.get(0).magh)
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

    fun getListCartDetail(token: String, req: GetCartReq){
        ApiCartDetailService.Api.api.getListCartDetail(token, req).enqueue(object : Callback<GetMainCDRes>{
            override fun onResponse(call: Call<GetMainCDRes>, response: Response<GetMainCDRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0) {
                        cartInterface.getListSuccess(response.body()!!.result)
                    }else{
                        cartInterface.cartEmpty()
                        println("giỏ hàng kh có sản phẩm") // hiện text view giỏ hàng trống
                    }
                }
            }

            override fun onFailure(call: Call<GetMainCDRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun removeCartDetail(token: String, req: DeleteCDReq){
        println("vô")
        ApiCartDetailService.Api.api.removeCartDetail(token ,req).enqueue(object : Callback<DeleteCDRes>{
            override fun onResponse(call: Call<DeleteCDRes>, response: Response<DeleteCDRes>) {
                println("call đc mà")
                if(response.isSuccessful){
                    println("Đã xóa sản phẩm khỏi giỏ hàng")
                    cartInterface.deleteSuccess()
                }
            }

            override fun onFailure(call: Call<DeleteCDRes>, t: Throwable) {
                Log.d("error" , ""+call)
                println("error "+call)
                t.printStackTrace()
            }

        })
    }
}