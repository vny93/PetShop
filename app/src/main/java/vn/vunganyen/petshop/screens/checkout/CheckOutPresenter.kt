package vn.vunganyen.petshop.screens.checkout

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartDetailService
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.model.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.cart.userUpdate.UserUpdateReq
import vn.vunganyen.petshop.data.model.cart.userUpdate.UserUpdateRes
import vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail.GetMainCDRes
import vn.vunganyen.petshop.data.model.user.addProfile.AddProfileReq
import vn.vunganyen.petshop.screens.home.HomeActivity

class CheckOutPresenter {
    var checkOutInterface : CheckOutInterface

    constructor(checkOutInterface: CheckOutInterface) {
        this.checkOutInterface = checkOutInterface
    }

    fun getListCartDetail(token: String, req: GetCartReq){
        ApiCartDetailService.Api.api.getListCartDetail(token, req).enqueue(object :
            Callback<GetMainCDRes> {
            override fun onResponse(call: Call<GetMainCDRes>, response: Response<GetMainCDRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0) {
                        checkOutInterface.getListSuccess(response.body()!!.result)
                    }
                }
            }

            override fun onFailure(call: Call<GetMainCDRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun validCheck(req: UserUpdateReq) {
        if (req.hotennguoinhan.isEmpty() || req.sdtnguoinhan.isEmpty() ||
            req.emailnguoinhan.isEmpty() || req.diachinguoinhan.isEmpty()){
            checkOutInterface.Empty()
            return
        }
        if (!HomeActivity.SDT.matcher(req.sdtnguoinhan).matches()) {
            checkOutInterface.PhoneIllegal()
            return
        }
        if (!HomeActivity.EMAIL_ADDRESS.matcher(req.emailnguoinhan).matches()) {
            checkOutInterface.EmailIllegal()
            return
        }
        checkOutInterface.ValidCheckSuccess(req)
    }

    fun  userUpdateCart(req : UserUpdateReq){
        ApiCartService.Api.api.userUpdateCard(HomeActivity.token,req).enqueue(object : Callback<UserUpdateRes>{
            override fun onResponse(call: Call<UserUpdateRes>, response: Response<UserUpdateRes>) {
                if(response.isSuccessful){
                    checkOutInterface.UpdateSuccess()
                }
            }

            override fun onFailure(call: Call<UserUpdateRes>, t: Throwable) {
                checkOutInterface.UpdateError()
            }

        })
    }
}