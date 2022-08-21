package vn.vunganyen.petshop.screens.admin.inputData.staff.getList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiStaffService
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductRes
import vn.vunganyen.petshop.data.model.admin.staff.getDetail.PostDetailStaffReq
import vn.vunganyen.petshop.data.model.admin.staff.getList.MainStaffMngRes

class StaffMngPresenter {
    var staffMngInterface : StaffMngInterface

    constructor(staffMngInterface: StaffMngInterface) {
        this.staffMngInterface = staffMngInterface
    }

    fun getList(token :String){
        ApiStaffService.Api.api.getList(token).enqueue(object : Callback<MainStaffMngRes>{
            override fun onResponse(call: Call<MainStaffMngRes>, response: Response<MainStaffMngRes>) {
                if(response.isSuccessful){
                    staffMngInterface.GetList(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainStaffMngRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkStaff(token : String, req: PostDetailStaffReq){
        ApiStaffService.Api.api.checkStaffUse(token, req).enqueue(object : Callback<CheckProductRes>{
            override fun onResponse(call: Call<CheckProductRes>, response: Response<CheckProductRes>) {
                if(response.isSuccessful){
                    staffMngInterface.RemoveFail()
                }
                else staffMngInterface.AllowRemove(req.manv)
            }
            override fun onFailure(call: Call<CheckProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun remove(token: String, req: PostDetailStaffReq){
        ApiStaffService.Api.api.deleteStaff(token, req).enqueue(object : Callback<CheckProductRes>{
            override fun onResponse(call: Call<CheckProductRes>, response: Response<CheckProductRes>) {
                if(response.isSuccessful){
                    staffMngInterface.RemoveSuccess()
                }
            }
            override fun onFailure(call: Call<CheckProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}