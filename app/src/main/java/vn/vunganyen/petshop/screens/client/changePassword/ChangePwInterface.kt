package vn.vunganyen.petshop.screens.client.changePassword

interface ChangePwInterface {
    fun ErrorIsEmpty()
    fun RegexPassword()
    fun ErrorConfirmPw()
    fun ErrorCurrentPw()
    fun ChangePwSuccess()
    fun ChangePwFail()
}