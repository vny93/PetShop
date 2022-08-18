package vn.vunganyen.petshop.screens.admin.inputData.mngBrand.listBrand

import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes

interface BrandMngInterface {
    fun GetListBrand(list : List<BrandDetailRes>)
    fun RemoveFail()
    fun AllowRemove(id : String)
    fun RemoveSuccess()
}