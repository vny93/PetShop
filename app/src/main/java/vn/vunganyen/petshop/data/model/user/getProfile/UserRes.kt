package vn.vunganyen.petshop.data.model.user.getProfile

import java.io.Serializable

data class UserRes(
    var makh: String,
    var hoten: String,
    var gioitinh: String,
    var diachi: String,
    var ngaysinh: String,
    var sdt: String,
    var email: String,
    var masothue: String,
    var tendangnhap: String
) : Serializable