package vn.vunganyen.petshop.data.model.product.get

import java.io.Serializable

data class ProductRes(
    val masp : String,
    val tensp : String,
    val gia : Float,
    val soluong : Int,
    val mota : String,
    val hinhanh : String,
    val maloaisp : Int,
    val mahang : String,
    val manhacc: String,
    val isgood : Int,
    val isnew : Int
) : Serializable