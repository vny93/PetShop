package vn.vunganyen.petshop.screens.client.myOrder.waitForConfirm

import vn.vunganyen.petshop.data.model.client.cart.add.AddCartRes

interface WaitInterface {
    fun getListSuccess(list : List<AddCartRes>)
    fun getEmpty()
}