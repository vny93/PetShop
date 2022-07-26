package vn.vunganyen.petshop.screens.explore

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiProductTypeService
import vn.vunganyen.petshop.data.model.productType.MainProTypeRes
import vn.vunganyen.petshop.data.model.productType.ProductTypeRes

class ExplorePresenter {
    var exploreInterface : ExploreInterface

    constructor(exploreInterface: ExploreInterface) {
        this.exploreInterface = exploreInterface
    }

    fun getList(){
        //nhớ truyền token nha, bh test trước chưa sd token
        ApiProductTypeService.Api.api.getList().enqueue(object : Callback<MainProTypeRes>{
            override fun onResponse(call: Call<MainProTypeRes>,response: Response<MainProTypeRes>) {
                println("vô nè")
                if(response.isSuccessful){
                    println("thành công")
                    response.body()?.let { exploreInterface.getListSuccess(it.result) }
                }
                println("Thất bại")
            }

            override fun onFailure(call: Call<MainProTypeRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}