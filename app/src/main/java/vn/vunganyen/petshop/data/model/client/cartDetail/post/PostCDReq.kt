package vn.vunganyen.petshop.data.model.client.cartDetail.post

import java.io.Serializable

data class PostCDReq(
    var magh : Int,
    var masp : String,
    var ctgia : Float,
    var ctsoluong : Int
) : Serializable