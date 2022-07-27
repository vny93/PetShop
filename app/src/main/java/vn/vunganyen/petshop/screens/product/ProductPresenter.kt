package vn.vunganyen.petshop.screens.product

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiProductService
import vn.vunganyen.petshop.data.model.product.MainProductRes
import vn.vunganyen.petshop.data.model.product.ProductReq


class ProductPresenter {
    var productInterface : ProductInterface

    constructor(productInterface: ProductInterface) {
        this.productInterface = productInterface
    }

    fun getListData(req : ProductReq){
        println("maloaisp: "+req.maloaisp)
        ApiProductService.Api.api.getListFK(req).enqueue(object : Callback<MainProductRes>{
            override fun onResponse(call: Call<MainProductRes>,response: Response<MainProductRes>) {
                println("vô")
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