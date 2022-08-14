package vn.vunganyen.petshop.screens.client.search

import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.client.product.get.ProductRes

interface SearchProInterface {
    fun getBrandName(list : List<BrandDetailRes>)
    fun getPRoduct(list : List<ProductRes>)
}