package vn.vunganyen.petshop.screens.admin.orderShip.shipperListOrder

import vn.vunganyen.petshop.data.model.client.cart.add.AddCartRes

interface ShipperOrderInterface {
    fun getList(list: List<AddCartRes>)
}