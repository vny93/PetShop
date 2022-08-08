package vn.vunganyen.petshop.screens.myOrder.transport

import vn.vunganyen.petshop.data.model.cart.add.AddCartRes

interface TransportInterface {
    fun getListSuccess(list : List<AddCartRes>)
    fun getEmpty()
}