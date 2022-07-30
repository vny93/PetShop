package vn.vunganyen.petshop.screens.cart

import vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail.GetCDSpRes


interface CartInterface {
    fun getListSuccess(list : List<GetCDSpRes>)
    fun cartEmpty()
    fun deleteSuccess()
}