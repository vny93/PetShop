package vn.vunganyen.petshop.screens.admin.inputData.mngBrand.insertBrand

interface InsertBrandInterface {
    fun Empty()
    fun PhoneIllegal()
    fun EmailIllegal()
    fun PhoneExist()
    fun ImageEmpty()
    fun BrandIdExist()
    fun EmailExist()
    fun InsertError()
    fun InsertSuccess()
}