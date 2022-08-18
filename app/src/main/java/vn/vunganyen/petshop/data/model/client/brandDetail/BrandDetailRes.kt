package vn.vunganyen.petshop.data.model.client.brandDetail

import java.io.Serializable

data class BrandDetailRes(
    val mahang : String,
    val tenhang : String,
    val email : String,
    val sdt: String,
    var logo : String,
    val mota : String
) : Serializable