package vn.vunganyen.petshop.screens.client.myOrderDetail

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartDetailService
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.model.client.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.client.cart.getCart.MainGetCartRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetMainCDRes

class OrderDetailPresenter {
    var orderDetailInterface : OrderDetailInterface

    constructor(oderDetailInterface: OrderDetailInterface) {
        this.orderDetailInterface = oderDetailInterface
    }

    fun get(token : String, req: GetCartReq){
        ApiCartService.Api.api.get(token,req).enqueue(object : Callback<MainGetCartRes>{
            override fun onResponse(call: Call<MainGetCartRes>,response: Response<MainGetCartRes>) {
                if(response.isSuccessful){
                    OrderDetailActivity.inforOrder = response.body()!!.result
                    getListCartDetail(token, req)
                }
            }

            override fun onFailure(call: Call<MainGetCartRes>, t: Throwable) {
                orderDetailInterface.ErrorGetCart()
            }

        })
    }

    fun getListCartDetail(token: String, req: GetCartReq){
        ApiCartDetailService.Api.api.getListCartDetail(token, req).enqueue(object :
            Callback<GetMainCDRes> {
            override fun onResponse(call: Call<GetMainCDRes>, response: Response<GetMainCDRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0) {
                        //lưu vào ds
                        orderDetailInterface.GetListSuccess(response.body()!!.result)
                    }
                }
            }

            override fun onFailure(call: Call<GetMainCDRes>, t: Throwable) {
                orderDetailInterface.ErrorGetList()
            }

        })
    }
}