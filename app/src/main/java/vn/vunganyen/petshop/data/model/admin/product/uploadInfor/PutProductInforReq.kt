package vn.vunganyen.petshop.data.model.admin.product.uploadInfor

data class PutProductInforReq(
    val masp :  String,
    val tensp : String,
    val gia : Float,
    val soluong : Int,
    val mota : String,
    val maloaisp : String,
    val mahang : String,
    val manhacc : String,
    val isgood : Int,
    val isnew : Int
)