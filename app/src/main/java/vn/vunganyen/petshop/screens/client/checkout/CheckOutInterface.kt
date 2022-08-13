package vn.vunganyen.petshop.screens.client.checkout

import vn.vunganyen.petshop.data.model.cart.userUpdate.UserUpdateReq
import vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail.GetCDSpRes

interface CheckOutInterface {
    fun getListSuccess(list: List<GetCDSpRes>)
    fun PhoneIllegal()
    fun EmailIllegal()
    fun Empty()
    fun UpdateSuccess()
    fun UpdateError()
    fun DateError()
    fun ValidCheckSuccess(req :UserUpdateReq)
}