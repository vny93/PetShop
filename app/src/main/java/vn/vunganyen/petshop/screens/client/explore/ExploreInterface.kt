package vn.vunganyen.petshop.screens.client.explore

import vn.vunganyen.petshop.data.model.client.productType.ProductTypeRes

interface ExploreInterface {
    fun getListSuccess(list : List<ProductTypeRes>)
}