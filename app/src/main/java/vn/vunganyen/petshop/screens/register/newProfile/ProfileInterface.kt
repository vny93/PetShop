package vn.vunganyen.petshop.screens.register.newProfile

interface ProfileInterface {
    fun Empty()
    fun PhoneIllegal()
    fun EmailIllegal()
    fun PhoneExist()
    fun EmailExist()
    fun AddSucces()
}