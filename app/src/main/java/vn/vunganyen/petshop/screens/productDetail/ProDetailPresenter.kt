package vn.vunganyen.petshop.screens.productDetail

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiBranDetailService
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.api.ApiProDetailService
import vn.vunganyen.petshop.data.model.brandDetail.MainBrandDetailRes
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.cart.add.MainCardRes
import vn.vunganyen.petshop.data.model.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.cart.getByStatus.MainCartStatusRes
import vn.vunganyen.petshop.data.model.proDetail.MainProDetailRes
import vn.vunganyen.petshop.data.model.proDetail.ProDetailReq
import vn.vunganyen.petshop.data.model.proDetail.ProDetailRes

class ProDetailPresenter {
    var proDetailInterface : ProDetailInterface

    constructor(proDetailInterface: ProDetailInterface) {
        this.proDetailInterface = proDetailInterface
    }

    fun getProDetail(req : ProDetailReq){
        ApiProDetailService.Api.api.getProDetail(req).enqueue(object : Callback<MainProDetailRes>{
            override fun onResponse(call: Call<MainProDetailRes>, response: Response<MainProDetailRes>) {
                if(response.isSuccessful){
                    response.body()?.let { getBranDetail(it.result, BrandDetailReq(response.body()!!.result.mahang) ) }
                }
            }

            override fun onFailure(call: Call<MainProDetailRes>, t: Throwable) {
                println("Product Detail: Không lấy được dữ liệu")
            }

        })
    }

    fun getBranDetail(res : ProDetailRes, req : BrandDetailReq){
        ApiBranDetailService.Api.api.getBrandDetail(req).enqueue(object : Callback<MainBrandDetailRes>{
            override fun onResponse(call: Call<MainBrandDetailRes>, response: Response<MainBrandDetailRes>) {
                if(response.isSuccessful){
                    response.body()?.let { proDetailInterface.getDetailSuccess(res, it.result) }
                }
            }

            override fun onFailure(call: Call<MainBrandDetailRes>, t: Throwable) {
                println("Brand in Product Detail: Không lấy được dữ liệu")
            }

        })
    }

    fun getCartByStatus(token : String, req : CartStatusReq ){
        ApiCartService.Api.api.getCartByStatus(token,req).enqueue(object : Callback<MainCartStatusRes>{
            override fun onResponse(call: Call<MainCartStatusRes>, response: Response<MainCartStatusRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0) {
                        println("kh null" + response.body()!!.result.get(0).magh)
                        //update chi tiết giỏ hàng từ mã gh với mã sp nè má
                    }else{
                        println("rỗng nha") // add giỏ hàng nè
                    }
                }
            }

            override fun onFailure(call: Call<MainCartStatusRes>, t: Throwable) {
                Log.d("error" , ""+call)
                println("error "+call)
                t.printStackTrace()
            }

        })
    }
}