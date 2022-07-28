package vn.vunganyen.petshop.screens.productDetail

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiBranDetailService
import vn.vunganyen.petshop.data.api.ApiCartDetailService
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.api.ApiProDetailService
import vn.vunganyen.petshop.data.model.brandDetail.MainBrandDetailRes
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.cart.add.AddCartReq
import vn.vunganyen.petshop.data.model.cart.add.MainAddCardRes
import vn.vunganyen.petshop.data.model.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.cart.getByStatus.MainCartStatusRes
import vn.vunganyen.petshop.data.model.cartDetail.post.PostCDReq
import vn.vunganyen.petshop.data.model.cartDetail.post.MainPostCDRes
import vn.vunganyen.petshop.data.model.cartDetail.update.PutCDReq
import vn.vunganyen.petshop.data.model.cartDetail.update.PutCDRes
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

    fun getCartByStatus(token : String, req : CartStatusReq, reqCartDetail : PostCDReq ){
        ApiCartService.Api.api.getCartByStatus(token,req).enqueue(object : Callback<MainCartStatusRes>{
            override fun onResponse(call: Call<MainCartStatusRes>, response: Response<MainCartStatusRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0) {
                        println("kh null" + response.body()!!.result.get(0).magh)


                        //update chi tiết giỏ hàng từ mã gh với mã sp nè má
                        //nếu mã sp chưa có trong chi tiết giỏ hàng thì add mới, nếu có rồi thì update
                    }else{
                        println("rỗng nha") // add giỏ hàng nè -> add chi tiết giỏ luôn
                        var reqCart = AddCartReq(req.makh)
                        addCart(token, reqCart, reqCartDetail)
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

    fun updateCartDetail(token : String, req : PutCDReq){
        ApiCartDetailService.Api.api.updateCartDetail(token, req).enqueue(object : Callback<PutCDRes>{
            override fun onResponse(call: Call<PutCDRes>, response: Response<PutCDRes>) {
                if(response.isSuccessful){
                    println(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<PutCDRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addCart(token: String, req : AddCartReq, reqCartDetail : PostCDReq){
        ApiCartService.Api.api.addCart(token, req).enqueue(object : Callback<MainAddCardRes>{
            override fun onResponse(call: Call<MainAddCardRes>,response: Response<MainAddCardRes>) {
                if(response.isSuccessful){
                    reqCartDetail.magh = response.body()!!.result.get(0).magh
                    addCartDetail(token,reqCartDetail)
                }
            }

            override fun onFailure(call: Call<MainAddCardRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addCartDetail(token: String, req : PostCDReq){
        println(req.magh)
        println(req.masp)
        println(req.gia)
        println(req.ctsoluong)
        ApiCartDetailService.Api.api.addCartDetail(token, req).enqueue(object : Callback<MainPostCDRes>{
            override fun onResponse(call: Call<MainPostCDRes>, response: Response<MainPostCDRes>) {
                if(response.isSuccessful){
                    println("Đã thêm vào giỏ hàng")
                    println("result: "+response.body()!!.result.masp)
                    proDetailInterface.addCDsuccess()
                }
            }

            override fun onFailure(call: Call<MainPostCDRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}