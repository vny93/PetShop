package vn.vunganyen.petshop.screens.admin.inputData.mngProduct.customProduct

import vn.vunganyen.petshop.data.model.admin.provider.getlist.ProviderRes
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.client.productType.ProductTypeRes

interface CustomPMngInterface {
    fun GetListBrand(list : List<BrandDetailRes>)
    fun GetListPT(list : List<ProductTypeRes>)
    fun GetListProvider(list : List<ProviderRes>)
    fun Empty()
    fun PriceError()
    fun NumberError()
    fun UpdateError()
    fun UpdateSuccess()
    fun InsertError()
    fun InsertSuccess()
    fun ImageEmpty()
    fun IdExis()
}