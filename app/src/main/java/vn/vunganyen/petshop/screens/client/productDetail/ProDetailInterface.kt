package vn.vunganyen.petshop.screens.client.productDetail
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.client.proDetail.ProDetailRes

interface ProDetailInterface {
    fun getDetailSuccess(res1 : ProDetailRes, re2 : BrandDetailRes)
    fun addCDsuccess()
    fun inventNum()
}