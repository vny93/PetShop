package vn.vunganyen.petshop.screens.client.cart

import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetCDSpRes


interface CartInterface {
    fun getListSuccess(list : List<GetCDSpRes>)
    fun cartEmpty()
    fun deleteSuccess()
}