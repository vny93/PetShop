package vn.vunganyen.petshop.data.model.cart.getCart

import java.io.Serializable

data class GetCartRes(
    val magh : Int,
    val ngaydat : String,
    val hotennguoinhan : String,
    val diachinguoinhan : String,
    val sdtnguoinhan : String,
    val emailnguoinhan : String,
    val tongtien : Float,
    val makh : String
) : Serializable