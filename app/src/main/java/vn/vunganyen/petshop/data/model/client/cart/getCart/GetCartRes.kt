package vn.vunganyen.petshop.data.model.client.cart.getCart

import java.io.Serializable

data class GetCartRes(
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
    val makh: String
) : Serializable