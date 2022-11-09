package vn.vunganyen.petshop.screens.admin.order.detailOrder

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartDetailService
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.api.ApiProductService
import vn.vunganyen.petshop.data.api.ApiStaffService
import vn.vunganyen.petshop.data.model.admin.cart.adminUpdate.AdminUpdateOrderReq
import vn.vunganyen.petshop.data.model.admin.cart.adminUpdate.UpdateStatusReq
import vn.vunganyen.petshop.data.model.admin.cart.getStaffName.GetStaffNameReq
import vn.vunganyen.petshop.data.model.admin.cart.getStaffName.MainGetStaffName
import vn.vunganyen.petshop.data.model.admin.staff.getDetail.PostDetailStaffReq
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.client.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.client.cart.userUpdate.UserUpdateRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetCDSpRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetMainCDRes
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderReq
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderRes
import vn.vunganyen.petshop.screens.client.checkout.CheckOutActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.util.*

class DetailOrderPresenter {
    var detailOrderInterface : DetailOrderInterface
    var count = 0

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
//        val current : String = SplashScreenActivity.formatdate.format(Date())
//        val result = req.ngaygiao.compareTo(current)
//        println("ngaydukien: "+req.ngaygiao)
//        println("ngayhientai: "+current)
//        println(result)
//        if(result < 0 || result == 0){
//            detailOrderInterface.DateError()
//            return
//        }
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