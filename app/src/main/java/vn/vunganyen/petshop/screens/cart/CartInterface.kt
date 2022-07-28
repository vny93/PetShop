package vn.vunganyen.petshop.screens.cart

import vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail.CartDetailSpRes


interface CartInterface {
    fun getListSuccess(list : List<CartDetailSpRes>)
    fun cartEmpty()
}