package vn.vunganyen.petshop.data.model.fastDelivery.order

data class SendDetailOrderReq(
    var mabk : Int,
    var masp : String,
    var tensp : String,
    var soluong : Int,
    var giatien : Float
)