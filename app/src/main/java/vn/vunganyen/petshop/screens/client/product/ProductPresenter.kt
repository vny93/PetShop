package vn.vunganyen.petshop.screens.client.product

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiProductService
import vn.vunganyen.petshop.data.model.client.product.get.MainProductRes
import vn.vunganyen.petshop.data.model.client.product.get.ProductReq


class ProductPresenter {
    var productInterface : ProductInterface

    constructor(productInterface: ProductInterface) {
        this.productInterface = productInterface
    }

    fun getListData(req : ProductReq){
        ApiProductService.Api.api.getListFK(req).enqueue(object : Callback<MainProductRes>{
            override fun onResponse(call: Call<MainProductRes>, response: Response<MainProductRes>) {
                if(response.isSuccessful){
                    response.body()?.let { productInterface.getListSuccess(it.result) }
                }
            }

            override fun onFailure(call: Call<MainProductRes>, t: Throwable) {
                println("Product: Không lấy được dữ liệu")
            }

        })
    }
}