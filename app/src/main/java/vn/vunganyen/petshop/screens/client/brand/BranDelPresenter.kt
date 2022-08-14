package vn.vunganyen.petshop.screens.client.brand

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiBrandService
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.client.brandDetail.MainBrandDetailRes

class BranDelPresenter {
    var brandDelInterface : BrandDelInterface

    constructor(brandDelInterface: BrandDelInterface) {
        this.brandDelInterface = brandDelInterface
    }

    fun getBranDetail(req: BrandDetailReq){
        ApiBrandService.Api.api.getBrandDetail(req).enqueue(object :
            Callback<MainBrandDetailRes> {
            override fun onResponse(call: Call<MainBrandDetailRes>, response: Response<MainBrandDetailRes>) {
                if(response.isSuccessful){
                    brandDelInterface.getBrandSuccess(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainBrandDetailRes>, t: Throwable) {
                println("Brand in Product Detail: Không lấy được dữ liệu")
            }

        })
    }

}