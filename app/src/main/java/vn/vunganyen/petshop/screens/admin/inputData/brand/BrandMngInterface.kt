package vn.vunganyen.petshop.screens.admin.inputData.brand

import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes

interface BrandMngInterface {
    fun getListBrand(list : List<BrandDetailRes>)
}