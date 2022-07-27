package vn.vunganyen.petshop.data.model.cartDetail.post

import java.io.Serializable

data class CartDetailRes2(
    val magh : Int,
    val masp : String,
    val gia : Float,
    val soluong : Int,
    val mapt : String,
    val soluongtra: Int
) : Serializable