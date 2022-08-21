package vn.vunganyen.petshop.data.model.admin.provider.getlist

import java.io.Serializable

data class ProviderRes(
    var manhacc : String,
    var tennhacc : String,
    var diachi : String,
    var sdt : String,
    var email : String
):Serializable