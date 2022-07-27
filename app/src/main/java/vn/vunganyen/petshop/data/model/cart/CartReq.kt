package vn.vunganyen.petshop.data.model.cart

import java.io.Serializable

data class CartReq(
    val ngaydat : String,
    val hotennguoinhan : String,
    val diachinguoinhan : String,
    val sdtnguoinhan : String,
    val emailnguoinhan : String,
    val tongtien : Float,
    val makh : String
): Serializable