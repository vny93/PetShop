package vn.vunganyen.petshop.screens.client.login

interface LoginInterface {
    fun loginEmpty()
    //fun userIllegal()
    fun loginSuccess()
    fun tokendie()
    fun loginError()
    fun wrongPass()
}