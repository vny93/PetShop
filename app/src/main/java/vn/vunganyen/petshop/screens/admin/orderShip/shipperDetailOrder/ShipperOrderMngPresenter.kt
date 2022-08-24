package vn.vunganyen.petshop.screens.admin.orderShip.shipperDetailOrder

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.api.ApiStaffService
import vn.vunganyen.petshop.data.model.admin.cart.shipperUpdate.ShipperUpdateOrderReq
import vn.vunganyen.petshop.data.model.admin.staff.getDetail.PostDetailStaffReq
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.client.cart.userUpdate.UserUpdateRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.util.*

class ShipperOrderMngPresenter {
    var shipperOrderMngInterface : ShipperOrderMngInterface

    constructor(shipperOrderMngInterface: ShipperOrderMngInterface) {
        this.shipperOrderMngInterface = shipperOrderMngInterface
    }

    fun getStaffReview(token: String, req: PostDetailStaffReq) {
        ApiStaffService.Api.api.getStaffDetail(token,req).enqueue(object : Callback<MainStaffRes>{
            override fun onResponse(call: Call<MainStaffRes>, response: Response<MainStaffRes>) {
                if(response.isSuccessful){
                    shipperOrderMngInterface.getReview(response.body()!!)
                }
            }
            override fun onFailure(call: Call<MainStaffRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getStaffShipper(token: String, req: PostDetailStaffReq) {
        ApiStaffService.Api.api.getStaffDetail(token,req).enqueue(object : Callback<MainStaffRes>{
            override fun onResponse(call: Call<MainStaffRes>, response: Response<MainStaffRes>) {
                if(response.isSuccessful){
                    shipperOrderMngInterface.getShipper(response.body()!!)
                }
            }
            override fun onFailure(call: Call<MainStaffRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun validCheckUpdate(token: String, req: ShipperUpdateOrderReq ){
        val current : String = SplashScreenActivity.formatdate.format(Date())
        val result = req.ngaydukien.compareTo(current)
        println("ngaydukien: "+req.ngaydukien)
        println("ngayhientai: "+current)
        if(result < 0 || result == 0){
            shipperOrderMngInterface.DateError()
            return
        }
        shipperUpdateOrder(token,req)
    }

    fun shipperUpdateOrder(token : String, req: ShipperUpdateOrderReq){
        ApiCartService.Api.api.shipperUpdateCard(token,req).enqueue(object : Callback<UserUpdateRes>{
            override fun onResponse(call: Call<UserUpdateRes>, response: Response<UserUpdateRes>) {
                if(response.isSuccessful){
                    shipperOrderMngInterface.UpdateSucces()
                }
                else shipperOrderMngInterface.UpdateError()
            }
            override fun onFailure(call: Call<UserUpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}