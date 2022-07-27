package vn.vunganyen.petshop.screens.productDetail
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.proDetail.ProDetailRes

interface ProDetailInterface {
    fun getDetailSuccess(res1 : ProDetailRes, re2 : BrandDetailRes)
}