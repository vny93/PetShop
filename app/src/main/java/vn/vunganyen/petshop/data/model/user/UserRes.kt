package vn.vunganyen.petshop.data.model.user

import java.io.Serializable

data class UserRes(
    val makh: String,
    val hoten: String,
    val gioitinh: String,
    val diachi: String,
    val ngaysinh: String,
    val sdt: String,
    val email: String,
    val masothue: String,
    val tendangnhap: String
) : Serializable