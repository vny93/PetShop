package vn.vunganyen.petshop.data.model.client.brandDetail

import java.io.Serializable

data class BrandDetailRes(
    val mahang : String,
    val tenhang : String,
    val email : String,
    val sdt: String,
    val logo : String,
    val mota : String
) : Serializable