package vn.vunganyen.petshop.screens.admin.inputData.mngBrand.updateBrand

interface BrandDetMngInterface {
    fun Empty()
    fun PhoneIllegal()
    fun EmailIllegal()
    fun PhoneExist()
    fun EmailExist()
    fun UpdateError()
    fun UpdateSuccess()
}