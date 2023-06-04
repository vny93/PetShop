package vn.vunganyen.petshop.screens.client.checkout

import android.location.Address
import android.location.Geocoder
import android.location.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.model.graphhopper.GraphhopperRes
import vn.vunganyen.petshop.data.api.*
import vn.vunganyen.petshop.data.model.client.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.client.cart.userUpdate.UserUpdateReq
import vn.vunganyen.petshop.data.model.client.cart.userUpdate.UserUpdateRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetCDSpRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetMainCDRes
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderReq
import vn.vunganyen.petshop.data.model.client.product.userUpdateOrder.UserOrderRes
import vn.vunganyen.petshop.data.model.district.DistrictRes
import vn.vunganyen.petshop.data.model.district.MainGetDistrictRes
import vn.vunganyen.petshop.data.model.fastDelivery.MainResponsePrice
import vn.vunganyen.petshop.data.model.fastDelivery.distance.RequestDistance
import vn.vunganyen.petshop.data.model.fastDelivery.RequestMass
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.io.IOException
import java.util.*

class CheckOutPresenter {
    var checkOutInterface : CheckOutInterface
    var count = 0

    constructor(checkOutInterface: CheckOutInterface) {
        this.checkOutInterface = checkOutInterface
    }

    fun getListCartDetail(token: String, req: GetCartReq){
        ApiCartDetailService.Api.api.getListCartDetail(token, req).enqueue(object :
            Callback<GetMainCDRes> {
            override fun onResponse(call: Call<GetMainCDRes>, response: Response<GetMainCDRes>) {
                if(response.isSuccessful){
                    if(response.body()!!.result.size > 0) {
                        //lưu vào ds
                        CheckOutActivity.listCartDetail = response.body()!!.result
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
            req.emailnguoinhan.isEmpty() || req.diachinguoinhan.isEmpty() || req.ngaydukien.isEmpty()){
            checkOutInterface.Empty()
            return
        }
        if (!SplashScreenActivity.SDT.matcher(req.sdtnguoinhan).matches()) {
            checkOutInterface.PhoneIllegal()
            return
        }
        if (!SplashScreenActivity.EMAIL_ADDRESS.matcher(req.emailnguoinhan).matches()) {
            checkOutInterface.EmailIllegal()
            return
        }
        val current : String = SplashScreenActivity.formatdate.format(Date())
        val result = req.ngaydukien.compareTo(current)
        println("ngaydukien: "+req.ngaydukien)
        println("ngayhientai: "+current)
        println(result)
        if(result < 0 || result == 0){
            checkOutInterface.DateError()
            return
        }
        checkOutInterface.ValidCheckSuccess(req)
    }

    fun  userUpdateCart(req : UserUpdateReq){
        ApiCartService.Api.api.userUpdateCard(SplashScreenActivity.token,req).enqueue(object : Callback<UserUpdateRes>{
            override fun onResponse(call: Call<UserUpdateRes>, response: Response<UserUpdateRes>) {
                if(response.isSuccessful){
                    //checkOutInterface.UpdateSuccess()
                    handleUpdateProduct(CheckOutActivity.listCartDetail)
                }
            }

            override fun onFailure(call: Call<UserUpdateRes>, t: Throwable) {
                checkOutInterface.UpdateError()
            }

        })
    }

    fun handleUpdateProduct(list : List<GetCDSpRes>){
        for(i in 0..list.size-1){
            var remainNum = list.get(i).soluong - list.get(i).ctsoluong
            var masp = list.get(i).masp
            calApiUpdateProduct(UserOrderReq(remainNum,masp), list.size-1)
        }
    }

    fun calApiUpdateProduct(req: UserOrderReq, size : Int){
        ApiProductService.Api.api.userUpdateOrder(SplashScreenActivity.token,req).enqueue(object : Callback<UserOrderRes>{
            override fun onResponse(call: Call<UserOrderRes>, response: Response<UserOrderRes>) {
                if(response.isSuccessful){
                    if(count == size){
                        checkOutInterface.UpdateSuccess()
                    }
                    count++
                }
            }

            override fun onFailure(call: Call<UserOrderRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getLocation(geocoder: Geocoder, adress : String){
        var addressList: List<Address>
        try {
            addressList = geocoder.getFromLocationName(adress, 1)
            if (addressList != null) {
                CheckOutActivity.lat = addressList.get(0).latitude
                CheckOutActivity.long = addressList.get(0).longitude
           //     getDistance(CheckOutActivity.lat, CheckOutActivity.long)
                callAPIGraphhopperRes(CheckOutActivity.lat, CheckOutActivity.long)
            }

        } catch (e: IOException) {
            println("Địa chỉ không hợp lệ")
            e.printStackTrace()
        }
    }

    fun getDistance(lat: Double, long: Double) {
        val currentLocation = Location("PetShop")
        currentLocation.setLatitude(SplashScreenActivity.STORE_LAT)
        currentLocation.setLongitude(SplashScreenActivity.STORE_LONG)
        val destination = Location("KH")
        destination.setLatitude(lat)
        destination.setLongitude(long)
        CheckOutActivity.distance = currentLocation.distanceTo(destination)/1000
        //Toast.makeText(this, "Distance between Sydney and Brisbane is \n " + String.format("%.2f", distance / 1000) + "km", Toast.LENGTH_SHORT).show();
        println("Distance: " + String.format("%.2f", CheckOutActivity.distance) + "km")
        println("Khối lượng nè: "+SplashScreenActivity.sumMass)
        getMassPrice(SplashScreenActivity.sumMass, CheckOutActivity.distance)
    }

    fun callAPIGraphhopperRes(lat: Double, long: Double){
        var pointSource = SplashScreenActivity.STORE_LAT.toString()+","+SplashScreenActivity.STORE_LONG.toString()
        var pointDes = lat.toString()+","+long.toString()
        var profile = "scooter"
        var locale = "vn"
        var calc_points = false
        var key = SplashScreenActivity.API_KEY
        GraphhopperService.Api.api.getDistanceApi(
            pointSource,pointDes,profile,locale, calc_points, key).enqueue(object : Callback<GraphhopperRes>{
            override fun onResponse(call: Call<GraphhopperRes>, response: Response<GraphhopperRes>) {
                println("--------API:"+response.raw().request.url)
                if(response.isSuccessful){
                    println("distance: "+response.body()!!.paths!!.get(0).distance)
                    var distance = (response.body()!!.paths!!.get(0).distance)?.div(1000)
                    CheckOutActivity.distance = distance!!.toFloat()
                    getMassPrice(SplashScreenActivity.sumMass, CheckOutActivity.distance)
                }
            }

            override fun onFailure(call: Call<GraphhopperRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getMassPrice(mass : Float, distance: Float){
        var req = RequestMass(mass)
        ApiFastDeliveryService.Api.api.getMassPrice(req).enqueue(object : Callback<MainResponsePrice>{
            override fun onResponse(call: Call<MainResponsePrice>, response: Response<MainResponsePrice>) {
                if(response.isSuccessful){
                    response.body()?.result?.get(0)?.let { checkOutInterface.getMassPrice(it.giatien) }
                    getDistancePrice(distance)
                }
            }

            override fun onFailure(call: Call<MainResponsePrice>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getDistancePrice(distance : Float){
        var req = RequestDistance(distance)
        ApiFastDeliveryService.Api.api.getDistancePrice(req).enqueue(object : Callback<MainResponsePrice>{
            override fun onResponse(call: Call<MainResponsePrice>, response: Response<MainResponsePrice>) {
                if(response.isSuccessful){
                    checkOutInterface.getDistancePrice(response.body()!!.result.get(0).giatien)
                }
            }

            override fun onFailure(call: Call<MainResponsePrice>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getDistrict(){
        ApiDistrictServer.Api.api.getDistrict1().enqueue(object : Callback<MainGetDistrictRes>{
            override fun onResponse(call: Call<MainGetDistrictRes>, response: Response<MainGetDistrictRes>) {
                if(response.isSuccessful){
                    checkOutInterface.getListDistrict(response.body()!!.districts)
                }
            }

            override fun onFailure(call: Call<MainGetDistrictRes>, t: Throwable) {
                println("UpdateStaffPst Error getDistrict()")
            }

        })
    }

    fun getWards(code : Long){
        ApiDistrictServer.Api.api.getDistrict2(code).enqueue(object : Callback<DistrictRes>{
            override fun onResponse(call: Call<DistrictRes>, response: Response<DistrictRes>) {
                if(response.isSuccessful){
                    checkOutInterface.getListWards(response.body()!!.wards)
                }
            }

            override fun onFailure(call: Call<DistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}