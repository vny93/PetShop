package vn.vunganyen.petshop.data.model.client.cartDetail.update

import java.io.Serializable

data class PutCDReq(
    val magh: Int,
    val masp: String,
    val ctgia: Float,
    val ctsoluong: Int
) : Serializable