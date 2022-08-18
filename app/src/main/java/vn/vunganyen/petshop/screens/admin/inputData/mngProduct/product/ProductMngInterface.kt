package vn.vunganyen.petshop.screens.admin.inputData.mngProduct.product

import vn.vunganyen.petshop.data.model.admin.product.getList.ProductOriginalRes

interface ProductMngInterface {
    fun GetList(list : List<ProductOriginalRes>)
    fun RemoveFail()
    fun AllowRemove(id : String)
    fun RemoveSuccess()
}