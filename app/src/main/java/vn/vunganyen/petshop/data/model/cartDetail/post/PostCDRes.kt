package vn.vunganyen.petshop.data.model.cartDetail.post

import java.io.Serializable

data class PostCDRes(
    val magh : Int,
    val masp : String,
    val gia : Float,
    val ctsoluong : Int
) : Serializable