package vn.vunganyen.petshop.screens.client.product

import vn.vunganyen.petshop.data.model.client.product.get.ProductRes


interface ProductInterface {
    fun getListSuccess(list : List<ProductRes>)
}