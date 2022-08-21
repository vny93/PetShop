package vn.vunganyen.petshop.screens.admin.inputData.staff.customStaff

import vn.vunganyen.petshop.data.model.client.register.findRole.FindRoleRes

interface CustomStaffInterface {
    fun getRoleSuccess(list : List<FindRoleRes>)
    fun getRoleId(id : Int)
    fun Empty()
    fun PhoneIllegal()
    fun EmailIllegal()
    fun PhoneExist()
    fun EmailExist()
    fun AddSucces()
    fun UpdateSucces()
    fun UpdateError()
    fun AccountExist()
    fun UserIllegal()
    fun PassIllegal()
    fun CheckSuccess()
    fun AddAuthError()
    fun StaffIdExist()
}