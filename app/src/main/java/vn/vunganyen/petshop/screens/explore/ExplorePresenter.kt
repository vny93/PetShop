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
        //cái này không cần token vẫn xem được
        ApiProductTypeService.Api.api.getList().enqueue(object : Callback<MainProTypeRes>{
            override fun onResponse(call: Call<MainProTypeRes>,response: Response<MainProTypeRes>) {
                if(response.isSuccessful){
                    response.body()?.let { exploreInterface.getListSuccess(it.result) }
                }
            }

            override fun onFailure(call: Call<MainProTypeRes>, t: Throwable) {
                println("Explore: Không lấy được dữ liệu")
            }

        })
    }
}