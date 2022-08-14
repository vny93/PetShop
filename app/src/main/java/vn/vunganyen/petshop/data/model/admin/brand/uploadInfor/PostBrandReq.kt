package vn.vunganyen.petshop.data.model.admin.brand.uploadInfor

import java.io.Serializable

data class PostBrandReq(
    val mahang : String,
    val tenhang : String,
    val email : String,
    val sdt: String,
    val mota : String
) : Serializable