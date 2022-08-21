package vn.vunganyen.petshop.screens.admin.profile.account

interface AccountInterface {
    fun ErrorIsEmpty()
    fun RegexPassword()
    fun ErrorConfirmPw()
    fun ErrorCurrentPw()
    fun ChangePwSuccess()
    fun ChangePwFail()
}