package vn.vunganyen.petshop.screens.client.home.seeAllProduct

import vn.vunganyen.petshop.data.model.product.get.ProductRes

interface SeeAllInterface {
    fun getListSuccess(list: List<ProductRes>)
}