package vn.vunganyen.petshop.screens.login

interface LoginInterface {
    fun loginEmpty()
    fun userIllegal()
    fun loginSuccess()
    fun tokendie()
    fun loginError()
    fun wrongPass()
}