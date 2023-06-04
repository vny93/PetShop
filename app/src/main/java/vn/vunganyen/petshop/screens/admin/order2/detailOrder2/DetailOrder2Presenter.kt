package vn.vunganyen.petshop.screens.admin.order2.detailOrder2

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.*
import vn.vunganyen.petshop.data.model.admin.cart.adminUpdate.UpdateStatusReq
import vn.vunganyen.petshop.data.model.fastDelivery.order.SendDetailOrderReq
import vn.vunganyen.petshop.data.model.fastDelivery.order.SendOrderReq
import vn.vunganyen.petshop.data.model.fastDelivery.order.SendOrderResponse
import vn.vunganyen.petshop.data.model.admin.staff.getDetail.PostDetailStaffReq
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.client.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.client.cart.userUpdate.UserUpdateRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetCDSpRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetMainCDRes
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderReq
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderRes
import vn.vunganyen.petshop.data.model.fastDelivery.detailStatus.DetailStatusReq
import vn.vunganyen.petshop.data.model.fastDelivery.detailStatus.DetailStatusRes
import vn.vunganyen.petshop.data.model.fastDelivery.order.SendDetailOrderRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class DetailOrder2Presenter {
    var detailOrder2Interface : DetailOrder2Interface
    var count = 0
    var temp = 0

    constructor(detailOrder2Interface: DetailOrder2Interface) {
        this.detailOrder2Interface = detailOrder2Interface
    }

    fun getStaffReview(token: String, req: PostDetailStaffReq) {
        ApiStaffService.Api.api.getStaffDetail(token,req).enqueue(object : Callback<MainStaffRes> {
            override fun onResponse(call: Call<MainStaffRes>, response: Response<MainStaffRes>) {
                if(response.isSuccessful){
                    detailOrder2Interface.getReview(response.body()!!)
                }
            }
            override fun onFailure(call: Call<MainStaffRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun updateStatus(token : String, req: UpdateStatusReq, check : Int){
        ApiCartService.Api.api.adminUpdateStatus(token,req).enqueue(object : Callback<UserUpdateRes>{
            override fun onResponse(call: Call<UserUpdateRes>, response: Response<UserUpdateRes>) {
                if(response.isSuccessful){
                    if(check == 1){
                        detailOrder2Interface.CancelOrderSuccess()
                    }
                    else{
                        detailOrder2Interface.UpdateStatusSucces()
                    }
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

    fun checkEmpty(size : String,req1 : SendOrderReq,req2: GetCartReq){
        if(size.isEmpty()){
            detailOrder2Interface.Empty()
            return
        }
        if (!SplashScreenActivity.SIZE.matcher(size).matches()) {
            detailOrder2Interface.SizeIllegal()
            return
        }
        sendOrder(req1,req2)
    }

    fun sendOrder(req1 : SendOrderReq,req2: GetCartReq){
        ApiFastDeliveryService.Api.api.insertParcel(req1).enqueue(object : Callback<SendOrderResponse>{
            override fun onResponse(call: Call<SendOrderResponse>, response: Response<SendOrderResponse>) {
                if(response.isSuccessful){
                    getListCartDetail2(SplashScreenActivity.token,req2)
                }
            }

            override fun onFailure(call: Call<SendOrderResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getListCartDetail2(token: String, req: GetCartReq){
        ApiCartDetailService.Api.api.getListCartDetail(token, req).enqueue(object : Callback<GetMainCDRes> {
            override fun onResponse(call: Call<GetMainCDRes>, response: Response<GetMainCDRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0) {
                        //lưu vào ds
                        for(i in 0..response.body()!!.result.size-1){
                            var req = SendDetailOrderReq(response.body()!!.result.get(i).magh,
                            response.body()!!.result.get(i).masp,response.body()!!.result.get(i).tensp,
                            response.body()!!.result.get(i).ctsoluong,response.body()!!.result.get(i).ctgia)
                            sendDetailOrder(req,response.body()!!.result.size-1)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<GetMainCDRes>, t: Throwable) {
            }

        })
    }

    fun sendDetailOrder(req2: SendDetailOrderReq, size: Int){
        ApiFastDeliveryService.Api.api.insertDetailParcel(req2).enqueue(object : Callback<SendDetailOrderRes>{
            override fun onResponse(call: Call<SendDetailOrderRes>, response: Response<SendDetailOrderRes>) {
                if(response.isSuccessful){
                    println(temp)
                    println(size)
                    if(temp==size) {
                        detailOrder2Interface.sendOrderSuccess()
                    }
                    temp++
                }
            }

            override fun onFailure(call: Call<SendDetailOrderRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun insertDetailStatus(req : DetailStatusReq){
        println("vô nè")
        ApiFastDeliveryService.Api.api.insertStatus(req).enqueue(object : Callback<DetailStatusRes>{
            override fun onResponse(call: Call<DetailStatusRes>, response: Response<DetailStatusRes>) {
                if(response.isSuccessful){
                    println("thành công")
                    detailOrder2Interface.addDetailStatus()
                }
            }

            override fun onFailure(call: Call<DetailStatusRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}