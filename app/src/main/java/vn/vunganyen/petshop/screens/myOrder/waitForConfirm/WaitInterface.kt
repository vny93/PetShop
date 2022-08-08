package vn.vunganyen.petshop.screens.myOrder.waitForConfirm

import vn.vunganyen.petshop.data.model.cart.add.AddCartRes

interface WaitInterface {
    fun getListSuccess(list : List<AddCartRes>)
    fun getEmpty()
}