package vn.vunganyen.petshop.data.model.client.user.addProfile

import java.io.Serializable

data class AddProfileReq(
    val hoten: String,
    val gioitinh: String,
    val diachi: String,
    val ngaysinh: String,
    val sdt: String,
    val email: String,
    val masothue: String,
    val tendangnhap: String
) : Serializable