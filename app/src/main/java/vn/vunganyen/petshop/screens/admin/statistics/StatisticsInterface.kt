package vn.vunganyen.petshop.screens.admin.statistics

import vn.vunganyen.petshop.data.model.admin.cart.getTurnover.TurnoverRes

interface StatisticsInterface {
    fun getFilter(list: List<TurnoverRes>)
    fun Empty()
    fun DateError()
}