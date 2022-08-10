package vn.vunganyen.petshop.screens.brand

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiBrandDetailService
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.brandDetail.MainBrandDetailRes
import vn.vunganyen.petshop.data.model.proDetail.ProDetailRes

class BranDelPresenter {
    var brandDelInterface : BrandDelInterface

    constructor(brandDelInterface: BrandDelInterface) {
        this.brandDelInterface = brandDelInterface
    }

    fun getBranDetail(req: BrandDetailReq){
        ApiBrandDetailService.Api.api.getBrandDetail(req).enqueue(object :
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