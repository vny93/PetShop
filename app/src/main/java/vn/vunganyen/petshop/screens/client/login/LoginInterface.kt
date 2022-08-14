package vn.vunganyen.petshop.screens.client.login

interface LoginInterface {
    fun loginEmpty()
    //fun userIllegal()
    fun loginClientSuccess()
    fun loginStaffSuccess()
    fun tokendie()
    fun loginError()
    fun wrongPass()
}