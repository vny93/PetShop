package vn.vunganyen.petshop.screens.admin.inputData.mngBrand.listBrand

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiBrandService
import vn.vunganyen.petshop.data.model.admin.brand.checkUse.CheckUseRes
import vn.vunganyen.petshop.data.model.admin.brand.uploadInfor.PostBrandRes
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffRes
import vn.vunganyen.petshop.data.model.client.brand.MainBrandRes
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.screens.admin.inputData.staff.getList.StaffManageActivity

class BrandMngPresenter {
    var brandMngInterface : BrandMngInterface

    constructor(brandMngInterface: BrandMngInterface) {
        this.brandMngInterface = brandMngInterface
    }

    fun getListBrand(){
        ApiBrandService.Api.api.getList().enqueue(object : Callback<MainBrandRes> {
            override fun onResponse(call: Call<MainBrandRes>, response: Response<MainBrandRes>) {
                if(response.isSuccessful){
                    BrandMngActivity.listBrand = response.body()!!.result as ArrayList<BrandDetailRes>
                    brandMngInterface.GetListBrand(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainBrandRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkBrandUse(token: String, req : BrandDetailReq){
        ApiBrandService.Api.api.checkBrandUser(token,req).enqueue(object : Callback<CheckUseRes>{
            override fun onResponse(call: Call<CheckUseRes>, response: Response<CheckUseRes>) {
                if(response.isSuccessful){
                    brandMngInterface.RemoveFail()
                }
                else{
                    brandMngInterface.AllowRemove(req.mahang)
                }
            }
            override fun onFailure(call: Call<CheckUseRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun removeBrand(token: String, req: BrandDetailReq){
        ApiBrandService.Api.api.removeBrand(token, req).enqueue(object : Callback<PostBrandRes>{
            override fun onResponse(call: Call<PostBrandRes>, response: Response<PostBrandRes>) {
                if(response.isSuccessful){
                    brandMngInterface.RemoveSuccess()
                }
            }
            override fun onFailure(call: Call<PostBrandRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getFilter(s : String){
        BrandMngActivity.listFilter = ArrayList<BrandDetailRes>()
        for(list in BrandMngActivity.listBrand){
            if(list.mahang.toUpperCase().contains(s.toUpperCase())){
                BrandMngActivity.listFilter.add(list)
            }
        }
        brandMngInterface.GetListBrand(BrandMngActivity.listFilter)
    }
}