package vn.vunganyen.petshop.data.model.cartDetail.post

data class CartDetailReq2(
    val magh : Int,
    val masp : String,
    val gia : Float,
    val soluong : Int,
    val mapt : String,
    val soluongtra: Int
)