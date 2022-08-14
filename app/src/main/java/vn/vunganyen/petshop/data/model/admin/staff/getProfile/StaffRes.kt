package vn.vunganyen.petshop.data.model.admin.staff.getProfile

import java.io.Serializable

data class StaffRes(
    var manv: String,
    var hoten: String,
    var gioitinh: String,
    var diachi: String,
    var ngaysinh: String,
    var sdt: String,
    var email: String,
    var tendangnhap: String
) : Serializable