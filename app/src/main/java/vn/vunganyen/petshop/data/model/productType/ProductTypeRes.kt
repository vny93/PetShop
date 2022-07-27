package vn.vunganyen.petshop.data.model.productType

import java.io.Serializable

data class ProductTypeRes(
    val maloaisp: Int,
    val tenloaisp: String,
    val hinhanh: String
): Serializable