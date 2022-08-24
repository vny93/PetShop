package vn.vunganyen.petshop.screens.admin.orderShip.shipperDetailOrder

import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes

interface ShipperOrderMngInterface {
    fun getReview(res: MainStaffRes)
    fun getShipper(res: MainStaffRes)
    fun UpdateSucces()
    fun UpdateError()
    fun DateError()
}