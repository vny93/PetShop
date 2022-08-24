package vn.vunganyen.petshop.screens.admin.statistics

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.petshop.data.api.ApiCartService
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.admin.cart.getTurnover.MainTurnoverRes
import vn.vunganyen.petshop.data.model.admin.cart.getTurnover.TurnoverReq
import vn.vunganyen.petshop.data.model.admin.cart.getTurnover.TurnoverRes
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.text.ParseException
import java.util.*

class StatisticsPresenter {
    var statisticsInterface : StatisticsInterface

    constructor(statisticsInterface: StatisticsInterface) {
        this.statisticsInterface = statisticsInterface
    }

    fun validCheck(token : String, req: TurnoverReq){
        if(req.dateFrom.isEmpty() || req.dateTo.isEmpty()){
            statisticsInterface.Empty()
            return
        }
        val result = req.dateTo.compareTo(req.dateFrom)
        if(result < 0 || result == 0){
            statisticsInterface.DateError()
            return
        }
        getFilter(token,req)
    }
    fun getFilter(token :String, req: TurnoverReq){
        ApiCartService.Api.api.getTurnover(token,req).enqueue(object : Callback<MainTurnoverRes>{
            override fun onResponse(call: Call<MainTurnoverRes>, response: Response<MainTurnoverRes>) {
                if(response.isSuccessful){
                //    statisticsInterface.getFilter(response.body()!!.result)
                    handle(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainTurnoverRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun handle(list :List<TurnoverRes> ){
        val dates: MutableList<String> = ArrayList<String>()
        val beginCalendar = Calendar.getInstance()
        val finishCalendar = Calendar.getInstance()
        try {
            beginCalendar.time = SplashScreenActivity.formatMonthYear.parse(StatisticsActivity.dateStr)
            finishCalendar.time = SplashScreenActivity.formatMonthYear.parse(StatisticsActivity.dateEnd)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        while (beginCalendar.compareTo(finishCalendar) <= 0) {
            val date = SplashScreenActivity.formatMonthYear.format(beginCalendar.time).uppercase(Locale.getDefault())
            dates.add(date)
            beginCalendar.add(Calendar.MONTH, 1)
        }
        for(i in 0..dates.size-1){
            var check = 0
            val str: List<String> = dates.get(i).split("-")
            var month = str.get(0).toInt()
            var year = str.get(1).toInt()
            for(j in 0..list.size-1){
                if(month == list.get(j).thang && year == list.get(j).nam){
                    StatisticsActivity.listNew.add(TurnoverRes(month,year,list.get(j).doanhthu))
                    check = 1
                }
            }
            if(check == 0){
                StatisticsActivity.listNew.add(TurnoverRes(month,year,0.0F))
            }
        }
        statisticsInterface.getFilter(StatisticsActivity.listNew)
    }



}