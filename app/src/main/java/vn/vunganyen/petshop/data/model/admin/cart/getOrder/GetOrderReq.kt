package vn.vunganyen.petshop.data.model.admin.cart.getOrder

data class GetOrderReq(
    val makh : Int,
    val dateFrom : String,
    val dateTo : String,
    val trangthai : String
)