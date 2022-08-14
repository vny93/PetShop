package vn.vunganyen.petshop.data.model.client.cartDetail.post

import java.io.Serializable

data class PostCDRes(
    val magh : Int,
    val masp : String,
    val ctgia : Float,
    val ctsoluong : Int
) : Serializable