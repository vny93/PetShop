package vn.vunganyen.petshop.screens.client.register.newRegister

interface RegisterInterface {
    fun RgtEmpty()
    fun UserIllegal()
    fun PassIllegal()
    fun AccountExist()
    fun NotFindRoleId()
    fun AddAuthSuccess(username : String, pass : String)
    fun AddAuthError()
}