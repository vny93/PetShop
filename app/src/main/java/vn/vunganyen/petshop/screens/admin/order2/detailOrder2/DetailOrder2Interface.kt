package vn.vunganyen.petshop.screens.admin.order2.detailOrder2

import vn.vunganyen.petshop.data.model.admin.cart.getStaffName.GetStaffNameRes
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes

interface DetailOrder2Interface {
//    fun getListShipper(list : List<GetStaffNameRes>)
    fun getReview(res: MainStaffRes)
    fun UpdateStatusSucces()
//    fun UpdateError()
    fun Empty()
//    fun DateError()
    fun CancelOrderSuccess()
    fun sendOrderSuccess()
    fun addDetailStatus()
    fun SizeIllegal()

}