package vn.vunganyen.petshop.data.model.client.cart.getByStatus

import vn.vunganyen.petshop.data.model.client.cart.add.AddCartRes

data class MainCartStatusRes(
    val result : List<AddCartRes>
)