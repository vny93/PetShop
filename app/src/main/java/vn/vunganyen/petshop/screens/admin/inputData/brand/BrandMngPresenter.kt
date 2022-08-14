package vn.vunganyen.petshop.screens.admin.inputData.brand

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiBrandService
import vn.vunganyen.petshop.data.model.client.brand.MainBrandRes

class BrandMngPresenter {
    var brandMngInterface : BrandMngInterface

    constructor(brandMngInterface: BrandMngInterface) {
        this.brandMngInterface = brandMngInterface
    }

    fun getListBrand(){
        ApiBrandService.Api.api.getList().enqueue(object : Callback<MainBrandRes> {
            override fun onResponse(call: Call<MainBrandRes>, response: Response<MainBrandRes>) {
                if(response.isSuccessful){
                    brandMngInterface.getListBrand(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainBrandRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}