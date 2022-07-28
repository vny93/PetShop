package vn.vunganyen.petshop.data.model.cartDetail.update

import java.io.Serializable

data class PutCDReq(
    val magh: Int,
    val masp: String,
    val gia: Float,
    val ctsoluong: Int
) : Serializable