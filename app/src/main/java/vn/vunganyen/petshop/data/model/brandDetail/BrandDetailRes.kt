package vn.vunganyen.petshop.data.model.brandDetail

import java.io.Serializable

data class BrandDetailRes(
    val mahang : String,
    val tenhang : String,
    val email : String,
    val sdt: String
) : Serializable