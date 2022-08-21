package vn.vunganyen.petshop.screens.admin.profile.information

interface InforInterface {
    fun Empty()
    fun PhoneIllegal()
    fun EmailIllegal()
    fun PhoneExist()
    fun EmailExist()
    fun UpdateSucces()
    fun UpdateError()
}