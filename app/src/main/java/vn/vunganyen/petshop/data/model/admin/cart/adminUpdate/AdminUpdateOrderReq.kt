package vn.vunganyen.petshop.data.model.admin.cart.adminUpdate

data class AdminUpdateOrderReq(
    val magh : Int,
    val ngaygiao: String,
    val trangthai : String,
    val manvduyet : String,
    val manvgiao : String
)