package vn.vunganyen.petshop.data.model.client.proDetail

import java.io.Serializable

data class ProDetailRes(
    val masp : String,
    val tensp : String,
    val gia : Float,
    val soluong : Int,
    val mota : String,
    val hinhanh : String,
    val maloaisp : String,
    val mahang : String,
    val manhacc: String,
    val isgood : Int,
    val isnew : Int,
    val ptgg : Int,
    val giagiam : Float
) : Serializable