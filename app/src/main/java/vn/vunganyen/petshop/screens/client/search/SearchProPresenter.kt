package vn.vunganyen.petshop.screens.client.search

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiBrandDetailService
import vn.vunganyen.petshop.data.api.ApiProductService
import vn.vunganyen.petshop.data.model.brand.MainBrandRes
import vn.vunganyen.petshop.data.model.product.get.MainProductRes
import vn.vunganyen.petshop.data.model.product.search.SearchProductReq

class SearchProPresenter {
    var searchProInterface : SearchProInterface

    constructor(searchProInterface: SearchProInterface) {
        this.searchProInterface = searchProInterface
    }

    fun getBrandName(){
        ApiBrandDetailService.Api.api.getList().enqueue(object : Callback<MainBrandRes>{
            override fun onResponse(call: Call<MainBrandRes>,response: Response<MainBrandRes>) {
                if(response.isSuccessful){
                    searchProInterface.getBrandName(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainBrandRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun searchProduct(req : SearchProductReq){
        ApiProductService.Api.api.searchProduct(req).enqueue(object : Callback<MainProductRes>{
            override fun onResponse(call: Call<MainProductRes>, response: Response<MainProductRes>) {
                if(response.isSuccessful){
                    searchProInterface.getPRoduct(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainProductRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun handleString(s: String): String {
        var str = s
        str = str.trim()
        val arrWord = str.split(" ");
        str = ""
        for (word in arrWord) {
            var newWord = word.toLowerCase()
            if (newWord.length > 0) {
             //   newWord = newWord.replaceFirst((newWord[0] + ""), (newWord[0] + "").toUpperCase())
                str += newWord + " "
            }
        }
        return str.trim()
    }
}