package vn.vunganyen.petshop.data.model.fastDelivery.order

data class SendOrderReq(
    var mabk : Int,
    var hotennguoinhan : String,
    var diachinguoinhan: String,
    var sdtnguoinhan: String,
    var ptthanhtoan: String,
    var sotien : Float,
    var tinhtrangthanhtoan: String,
    var khoiluong : Float,
    var kichthuoc: String,
    var phigiao : Float,
    var htvanchuyen: String,
    var ghichu: String,
    var ngaygui: String,
    var mach: String
)