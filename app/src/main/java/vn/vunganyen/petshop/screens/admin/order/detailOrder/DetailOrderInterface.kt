package vn.vunganyen.petshop.screens.admin.order.detailOrder

import vn.vunganyen.petshop.data.model.admin.cart.getStaffName.GetStaffNameRes
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes

interface DetailOrderInterface {
    fun getListShipper(list : List<GetStaffNameRes>)
    fun getReview(res: MainStaffRes)
    fun UpdateSucces()
    fun UpdateError()
    fun Empty()
    fun DateError()
    fun CancelOrderSuccess()
}