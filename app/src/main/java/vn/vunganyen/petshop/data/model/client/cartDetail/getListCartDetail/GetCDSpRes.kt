package vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail

import java.io.Serializable

data class GetCDSpRes(
    val magh : Int,
    val masp : String,
    val ctgia : Float,
    val ctsoluong : Int,
    val mapt : String,
    val soluongtra: Int,
    val tensp : String,
    val hinhanh : String,
    val soluong : Int,
    val gia: Float
) : Serializable