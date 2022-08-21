package vn.vunganyen.petshop.screens.admin.inputData.mngProduct.listProduct

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiProductService
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductReq
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductRes
import vn.vunganyen.petshop.data.model.admin.product.getList.MainProductOriginalRes

class ProductMngPresenter {
    var productMngInterface : ProductMngInterface

    constructor(productMngInterface: ProductMngInterface) {
        this.productMngInterface = productMngInterface
    }

    fun getList(token : String){
        ApiProductService.Api.api.getList(token).enqueue(object : Callback<MainProductOriginalRes>{
            override fun onResponse(call: Call<MainProductOriginalRes>,response: Response<MainProductOriginalRes>) {
                if(response.isSuccessful){
                    productMngInterface.GetList(response.body()!!.result)
                }
            }
            override fun onFailure(call: Call<MainProductOriginalRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun checkUse(token : String, req: CheckProductReq){
        ApiProductService.Api.api.checkProductUse(token,req).enqueue(object : Callback<CheckProductRes>{
            override fun onResponse(call: Call<CheckProductRes>,response: Response<CheckProductRes>) {
                if(response.isSuccessful){
                    productMngInterface.RemoveFail()
                }
                else productMngInterface.AllowRemove(req.masp)
            }
            override fun onFailure(call: Call<CheckProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun remove(token : String, req : CheckProductReq){
        ApiProductService.Api.api.removeProduct(token, req).enqueue(object : Callback<CheckProductRes>{
            override fun onResponse(call: Call<CheckProductRes>,response: Response<CheckProductRes>) {
                if(response.isSuccessful){
                    productMngInterface.RemoveSuccess()
                }
            }

            override fun onFailure(call: Call<CheckProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}