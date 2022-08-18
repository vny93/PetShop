package vn.vunganyen.petshop.screens.admin.inputData.mngProductType.listPT

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiProductTypeService
import vn.vunganyen.petshop.data.model.admin.productType.checkPTUse.CheckPTReq
import vn.vunganyen.petshop.data.model.admin.productType.checkPTUse.CheckPTRes
import vn.vunganyen.petshop.data.model.client.productType.MainProTypeRes

class ProTypeMngPresenter {
    var proTypeMngInterface : ProTypeMngInterface

    constructor(proTypeMngInterface: ProTypeMngInterface) {
        this.proTypeMngInterface = proTypeMngInterface
    }

    fun getList(){
        ApiProductTypeService.Api.api.getList().enqueue(object : Callback<MainProTypeRes>{
            override fun onResponse(call: Call<MainProTypeRes>,response: Response<MainProTypeRes>){
                if(response.isSuccessful){
                    proTypeMngInterface.GetList(response.body()!!.result)
                }
            }
            override fun onFailure(call: Call<MainProTypeRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun checkUse(token :String, req: CheckPTReq){
        ApiProductTypeService.Api.api.checkPTUse(token,req).enqueue(object : Callback<CheckPTRes>{
            override fun onResponse(call: Call<CheckPTRes>, response: Response<CheckPTRes>) {
                if(response.isSuccessful){
                    proTypeMngInterface.RemoveFail()
                }
                else proTypeMngInterface.AllowRemove(req.maloaisp)
            }

            override fun onFailure(call: Call<CheckPTRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun removePT(token : String, req : CheckPTReq){
        ApiProductTypeService.Api.api.removeProductType(token,req).enqueue(object : Callback<CheckPTRes>{
            override fun onResponse(call: Call<CheckPTRes>, response: Response<CheckPTRes>) {
                if(response.isSuccessful){
                    proTypeMngInterface.RemoveSuccess()
                }
            }
            override fun onFailure(call: Call<CheckPTRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}