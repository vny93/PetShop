package vn.vunganyen.petshop.screens.client.home.seeAllProduct

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiProductService
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.client.product.get.MainProductRes

class SeeAllPresenter {
    var seeAllInterface : SeeAllInterface

    constructor(seeAllInterface: SeeAllInterface) {
        this.seeAllInterface = seeAllInterface
    }

    fun getProductByBrand(req : BrandDetailReq){
        ApiProductService.Api.api.getListByBrand(req).enqueue(object : Callback<MainProductRes>{
            override fun onResponse(call: Call<MainProductRes>,response: Response<MainProductRes>) {
                if(response.isSuccessful){
                    seeAllInterface.getListSuccess(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}