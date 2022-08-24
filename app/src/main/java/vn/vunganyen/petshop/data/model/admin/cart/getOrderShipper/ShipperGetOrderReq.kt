package vn.vunganyen.petshop.data.model.admin.cart.getOrderShipper

data class ShipperGetOrderReq(
    val manv : String,
    val dateFrom : String,
    val dateTo : String,
    val trangthai : String
)