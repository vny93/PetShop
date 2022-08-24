package vn.vunganyen.petshop.screens.admin.order.listOrder

import vn.vunganyen.petshop.data.model.client.cart.add.AddCartRes

interface OrderMngInterface {
    fun getList(list: List<AddCartRes>)
}