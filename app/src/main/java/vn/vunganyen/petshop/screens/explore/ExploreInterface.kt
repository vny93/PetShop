package vn.vunganyen.petshop.screens.explore

import vn.vunganyen.petshop.data.model.productType.ProductTypeRes

interface ExploreInterface {
    fun getListSuccess(list : List<ProductTypeRes>)
}