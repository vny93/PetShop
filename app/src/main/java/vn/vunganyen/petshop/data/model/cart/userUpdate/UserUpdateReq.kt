package vn.vunganyen.petshop.data.model.cart.userUpdate

data class UserUpdateReq(
    var hotennguoinhan: String,
    var diachinguoinhan: String,
    var sdtnguoinhan: String,
    var emailnguoinhan: String,
    var tongtien: Float,
    var trangthai: String,
    var magh: Int
)