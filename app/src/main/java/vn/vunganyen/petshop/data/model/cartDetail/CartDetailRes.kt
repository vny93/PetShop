package vn.vunganyen.petshop.data.model.cartDetail

import java.io.Serializable

data class CartDetailRes(
    val magh : Int,
    val ngaydat : String,
    val hotennguoinhan : String,
    val diachinguoinhan : String,
    val sdtnguoinhan : String,
    val emailnguoinhan : String,
    val tongtien : Float,
    val makh : String
) : Serializable