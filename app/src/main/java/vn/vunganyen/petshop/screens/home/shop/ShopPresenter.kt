package vn.vunganyen.petshop.screens.home.shop

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiProductService
import vn.vunganyen.petshop.data.model.product.get.MainProductRes

class ShopPresenter {
    var shopInterface : ShopInterface

    constructor(shopInterface: ShopInterface) {
        this.shopInterface = shopInterface
    }

    fun getListDiscount(){
        ApiProductService.Api.api.getListDiscount().enqueue(object : Callback<MainProductRes>{
            override fun onResponse(call: Call<MainProductRes>,response: Response<MainProductRes>) {
                if(response.isSuccessful){
                    FragmentShop.listDiscount = response.body()!!.result
                    getListIsNew()
                }
            }

            override fun onFailure(call: Call<MainProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getListIsNew(){
        ApiProductService.Api.api.getListIsNew().enqueue(object : Callback<MainProductRes>{
            override fun onResponse(call: Call<MainProductRes>,response: Response<MainProductRes>) {
                if(response.isSuccessful){
                    FragmentShop.listIsNew = response.body()!!.result
                    getListIsGood()
                }
            }

            override fun onFailure(call: Call<MainProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getListIsGood(){
        ApiProductService.Api.api.getListIsGood().enqueue(object : Callback<MainProductRes>{
            override fun onResponse(call: Call<MainProductRes>,response: Response<MainProductRes>) {
                if(response.isSuccessful){
                    FragmentShop.listIsGood = response.body()!!.result
                    shopInterface.getListSuccess()
                }
            }

            override fun onFailure(call: Call<MainProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}