package vn.vunganyen.petshop.screens.client.orderClient

import vn.vunganyen.petshop.data.model.client.cart.add.AddCartRes

interface OrderClientInterface {
    fun getList(list: List<AddCartRes>)
    fun CancelOrderSuccess()
}