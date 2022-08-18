package vn.vunganyen.petshop.screens.admin.inputData.mngProductType.listPT

import vn.vunganyen.petshop.data.model.client.productType.ProductTypeRes

interface ProTypeMngInterface {
    fun GetList(list : List<ProductTypeRes>)
    fun RemoveFail()
    fun AllowRemove(id : String)
    fun RemoveSuccess()
}