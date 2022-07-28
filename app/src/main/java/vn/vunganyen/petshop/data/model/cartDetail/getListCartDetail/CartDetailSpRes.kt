package vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail

import java.io.Serializable

data class CartDetailSpRes(
    val magh : Int,
    val masp : String,
    val gia : Float,
    val soluong : Int,
    val mapt : String,
    val soluongtra: Int,
    val tensp : String,
    val hinhanh : String
) : Serializable