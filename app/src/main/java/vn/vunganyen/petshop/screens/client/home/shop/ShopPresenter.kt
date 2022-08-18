package vn.vunganyen.petshop.screens.client.home.shop

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiBrandService
import vn.vunganyen.petshop.data.api.ApiProductService
import vn.vunganyen.petshop.data.model.client.brand.MainBrandRes
import vn.vunganyen.petshop.data.model.client.product.get.MainProductRes

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
                println("Explore: Không lấy được dữ liệu")
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
                println("Explore: Không lấy được dữ liệu")
            }

        })
    }

    fun getListIsGood(){
        ApiProductService.Api.api.getListIsGood().enqueue(object : Callback<MainProductRes>{
            override fun onResponse(call: Call<MainProductRes>,response: Response<MainProductRes>) {
                if(response.isSuccessful){
                    FragmentShop.listIsGood = response.body()!!.result
                    getListBrand()
                }
            }

            override fun onFailure(call: Call<MainProductRes>, t: Throwable) {
                println("Explore: Không lấy được dữ liệu")
            }

        })
    }

    fun getListBrand(){
        ApiBrandService.Api.api.getList().enqueue(object : Callback<MainBrandRes>{
            override fun onResponse(call: Call<MainBrandRes>, response: Response<MainBrandRes>) {
                if(response.isSuccessful){
                    FragmentShop.listBrand = response.body()!!.result
                    shopInterface.getListSuccess()
                }
            }

            override fun onFailure(call: Call<MainBrandRes>, t: Throwable) {
                println("Explore: Không lấy được dữ liệu")
            }

        })
    }
}