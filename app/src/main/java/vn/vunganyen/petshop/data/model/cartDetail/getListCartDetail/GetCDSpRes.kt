package vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail

import java.io.Serializable

data class GetCDSpRes(
    val magh : Int,
    val masp : String,
    val gia : Float,
    val ctsoluong : Int,
    val mapt : String,
    val soluongtra: Int,
    val tensp : String,
    val hinhanh : String,
    val soluong : Int
) : Serializable