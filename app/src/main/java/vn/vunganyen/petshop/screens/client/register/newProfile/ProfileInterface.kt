package vn.vunganyen.petshop.screens.client.register.newProfile

interface ProfileInterface {
    fun Empty()
    fun PhoneIllegal()
    fun EmailIllegal()
    fun PhoneExist()
    fun EmailExist()
    fun AddSucces()
    fun UpdateSucces()
    fun UpdateError()
    fun OrlError()
}