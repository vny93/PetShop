package vn.vunganyen.petshop.screens.product

import vn.vunganyen.petshop.data.model.product.get.ProductRes


interface ProductInterface {
    fun getListSuccess(list : List<ProductRes>)
}