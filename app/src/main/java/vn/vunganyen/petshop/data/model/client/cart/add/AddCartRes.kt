package vn.vunganyen.petshop.data.model.client.cart.add

import java.io.Serializable

data class AddCartRes(
    val magh : Int,
    val ngaydat : String,
    val hotennguoinhan : String,
    val diachinguoinhan : String,
    val sdtnguoinhan : String,
    val emailnguoinhan: String,
    val ngaygiao: String,
    val tongtien : Float,
    val trangthai: String,
    val ngaydukien: String,
    val manvduyet: String,
    val manvgiao: String,
    val makh: Int,
    val ptthanhtoan : String,
    val phigiao : Float,
    val khoiluong : Float,
    val ttthanhtoan : String,
    val htvanchuyen : String
    ): Serializable