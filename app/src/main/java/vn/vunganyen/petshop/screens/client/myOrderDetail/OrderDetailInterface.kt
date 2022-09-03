package vn.vunganyen.petshop.screens.client.myOrderDetail

import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetCDSpRes

interface OrderDetailInterface {
    fun GetListSuccess(list: List<GetCDSpRes>)
    fun ErrorGetCart()
    fun ErrorGetList()
    fun getStaff(res : StaffRes)
}