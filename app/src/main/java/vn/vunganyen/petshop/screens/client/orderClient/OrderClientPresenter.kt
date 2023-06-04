package vn.vunganyen.petshop.screens.client.orderClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartDetailService
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.api.ApiProductService
import vn.vunganyen.petshop.data.model.admin.cart.adminUpdate.UpdateStatusReq
import vn.vunganyen.petshop.data.model.admin.cart.getOrder.GetOrderReq
import vn.vunganyen.petshop.data.model.admin.cart.userUpdate.UserUpdateStatusReq
import vn.vunganyen.petshop.data.model.client.cart.add.MainAddCardRes
import vn.vunganyen.petshop.data.model.client.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.client.cart.userUpdate.UserUpdateRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetCDSpRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetMainCDRes
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderReq
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class OrderClientPresenter {
    var orderClientInterface : OrderClientInterface
    var count = 0

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

    fun updateStatus(token : String, req: UserUpdateStatusReq){
        ApiCartService.Api.api.userUpdateStatus(token,req).enqueue(object : Callback<UserUpdateRes>{
            override fun onResponse(call: Call<UserUpdateRes>, response: Response<UserUpdateRes>) {
                if(response.isSuccessful){
                    orderClientInterface.CancelOrderSuccess()
                }
            }

            override fun onFailure(call: Call<UserUpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
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
                        handleUpdateProduct(response.body()!!.result)
                    }
                }
            }

            override fun onFailure(call: Call<GetMainCDRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun handleUpdateProduct(list : List<GetCDSpRes>){
        for(i in 0..list.size-1){
            println(list.get(i).soluong)
            println(list.get(i).ctsoluong)
            var remainNum = list.get(i).soluong + list.get(i).ctsoluong
            println(remainNum)
            var masp = list.get(i).masp
            calApiUpdateProduct(UserOrderReq(remainNum,masp), list.size-1)
        }
    }

    fun calApiUpdateProduct(req: UserOrderReq, size : Int){
        ApiProductService.Api.api.userUpdateOrder(SplashScreenActivity.token,req).enqueue(object : Callback<UserOrderRes>{
            override fun onResponse(call: Call<UserOrderRes>, response: Response<UserOrderRes>) {
                if(response.isSuccessful){
                    if(count == size){
                        return
                    }
                    count++
                }
            }

            override fun onFailure(call: Call<UserOrderRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}