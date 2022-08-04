package vn.vunganyen.petshop.screens.register.newRegister

import vn.vunganyen.petshop.data.model.register.addAuth.AddAuthReq

interface RegisterInterface {
    fun RgtEmpty()
    fun UserIllegal()
    fun PassIllegal()
    fun AccountExist()
    fun NotFindRoleId()
    fun AddAuthSuccess(username : String, pass : String)
    fun AddAuthError()
}