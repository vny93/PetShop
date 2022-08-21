package vn.vunganyen.petshop.screens.admin.inputData.staff.getList

import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffRes

interface StaffMngInterface {
    fun GetList(list : List<StaffRes>)
    fun RemoveFail()
    fun AllowRemove(id : String)
    fun RemoveSuccess()
}