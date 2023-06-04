package vn.vunganyen.petshop.screens.client.checkout

import vn.vunganyen.petshop.data.model.client.cart.userUpdate.UserUpdateReq
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetCDSpRes
import vn.vunganyen.petshop.data.model.district.DistrictRes
import vn.vunganyen.petshop.data.model.district.WardsRes

interface CheckOutInterface {
    fun getListSuccess(list: List<GetCDSpRes>)
    fun PhoneIllegal()
    fun EmailIllegal()
    fun Empty()
    fun UpdateSuccess()
    fun UpdateError()
    fun DateError()
    fun ValidCheckSuccess(req :UserUpdateReq)
    fun getMassPrice(price : Float)
    fun getDistancePrice(price : Float)
    fun getListDistrict(list: List<DistrictRes>)
    fun getListWards(list: List<WardsRes>)
}