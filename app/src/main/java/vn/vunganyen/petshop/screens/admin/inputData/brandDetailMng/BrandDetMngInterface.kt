package vn.vunganyen.petshop.screens.admin.inputData.brandDetailMng

interface BrandDetMngInterface {
    fun Empty()
    fun PhoneIllegal()
    fun EmailIllegal()
    fun PhoneExist()
    fun EmailExist()
    fun UpdateError()
    fun UpdateSuccess()
}