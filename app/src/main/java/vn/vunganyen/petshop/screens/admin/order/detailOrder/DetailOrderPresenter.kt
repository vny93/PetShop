package vn.vunganyen.petshop.screens.admin.order.detailOrder

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.api.ApiStaffService
import vn.vunganyen.petshop.data.model.admin.cart.adminUpdate.AdminUpdateOrderReq
import vn.vunganyen.petshop.data.model.admin.cart.adminUpdate.UpdateStatusReq
import vn.vunganyen.petshop.data.model.admin.cart.getStaffName.GetStaffNameReq
import vn.vunganyen.petshop.data.model.admin.cart.getStaffName.MainGetStaffName
import vn.vunganyen.petshop.data.model.admin.staff.getDetail.PostDetailStaffReq
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.client.cart.userUpdate.UserUpdateRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.util.*

class DetailOrderPresenter {
    var detailOrderInterface : DetailOrderInterface

    constructor(detailOrderInterface: DetailOrderInterface) {
        this.detailOrderInterface = detailOrderInterface
    }

    fun getListShipper(token : String, req: GetStaffNameReq){
        ApiStaffService.Api.api.getNameStaff(token,req).enqueue(object : Callback<MainGetStaffName>{
            override fun onResponse(call: Call<MainGetStaffName>, response: Response<MainGetStaffName>) {
                if(response.isSuccessful){
                    detailOrderInterface.getListShipper(response.body()!!.result)
                }
            }
            override fun onFailure(call: Call<MainGetStaffName>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getStaffReview(token: String, req: PostDetailStaffReq) {
        ApiStaffService.Api.api.getStaffDetail(token,req).enqueue(object : Callback<MainStaffRes>{
            override fun onResponse(call: Call<MainStaffRes>, response: Response<MainStaffRes>) {
                if(response.isSuccessful){
                    detailOrderInterface.getReview(response.body()!!)
                }
            }
            override fun onFailure(call: Call<MainStaffRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun validCheckUpdate(token : String, req : AdminUpdateOrderReq){
        if(req.manvgiao.isEmpty() || req.ngaygiao.isEmpty()){
            detailOrderInterface.Empty()
            return
        }
        val current : String = SplashScreenActivity.formatdate.format(Date())
        val result = req.ngaygiao.compareTo(current)
        println("ngaydukien: "+req.ngaygiao)
        println("ngayhientai: "+current)
        println(result)
        if(result < 0 || result == 0){
            detailOrderInterface.DateError()
            return
        }
        updateOrder(token,req)
    }

    fun updateOrder(token : String, req : AdminUpdateOrderReq){
        ApiCartService.Api.api.adminUpdateCard(token,req).enqueue(object : Callback<UserUpdateRes>{
            override fun onResponse(call: Call<UserUpdateRes>, response: Response<UserUpdateRes>) {
                if(response.isSuccessful){
                    detailOrderInterface.UpdateSucces()
                }
                else{
                    detailOrderInterface.UpdateError()
                }
            }

            override fun onFailure(call: Call<UserUpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateStatus(token : String, req: UpdateStatusReq){
        ApiCartService.Api.api.adminUpdateStatus(token,req).enqueue(object : Callback<UserUpdateRes>{
            override fun onResponse(call: Call<UserUpdateRes>, response: Response<UserUpdateRes>) {
                if(response.isSuccessful){
                    detailOrderInterface.CancelOrderSuccess()
                }
            }

            override fun onFailure(call: Call<UserUpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}