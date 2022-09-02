package vn.vunganyen.petshop.screens.admin.statistics

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.admin.AdapterStatistics
import vn.vunganyen.petshop.data.model.admin.cart.getTurnover.TurnoverReq
import vn.vunganyen.petshop.data.model.admin.cart.getTurnover.TurnoverRes
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityStatisticsBinding
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class StatisticsActivity : AppCompatActivity(), StatisticsInterface {
    lateinit var  binding : ActivityStatisticsBinding
    lateinit var statisticsPresenter: StatisticsPresenter
    var adapter : AdapterStatistics = AdapterStatistics()
    var c = Calendar.getInstance()
    var day = c.get(Calendar.DAY_OF_MONTH)
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var dialog: StartAlertDialog = StartAlertDialog()
    companion object{
        var sum = 0.0F
        lateinit var listNew : ArrayList<TurnoverRes>
        var dateStr = ""
        var dateEnd = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statisticsPresenter = StatisticsPresenter(this)
        setEvent()
        setToolbar()
    }

    fun setEvent() {
        binding.imvDateFrom.setOnClickListener {
            val dpd =
                this?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            binding.selectFrom.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                            day = mDay
                            month = mMonth
                            year = mYear
                        },
                        year,
                        month,
                        day
                    )
                }
            if (dpd != null) {
                dpd.show()
            }
        }

        binding.imvDateTo.setOnClickListener {
            val dpd =
                this?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            binding.selectTo.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                            day = mDay
                            month = mMonth
                            year = mYear
                        },
                        year,
                        month,
                        day
                    )
                }
            if (dpd != null) {
                dpd.show()
            }
        }

        binding.btnFilter.setOnClickListener{
            var strFrom = binding.selectFrom.text.toString()
            var strTo = binding.selectTo.text.toString()
            var dateFrom = ""
            var dateTo = ""
            if(!strFrom.equals("")){
                val mdateFrom : Date = SplashScreenActivity.formatdate1.parse(binding.selectFrom.text.toString())
                dateFrom = SplashScreenActivity.formatdate.format(mdateFrom)
                println("dateFrom: "+dateFrom)
                dateStr = SplashScreenActivity.formatMonthYear.format(mdateFrom)
            }
            if(!strTo.equals("")){
                val mdateTo : Date = SplashScreenActivity.formatdate1.parse(binding.selectTo.text.toString())
                dateTo = SplashScreenActivity.formatdate.format(mdateTo)
                println("dateTo: "+dateTo)
                dateEnd = SplashScreenActivity.formatMonthYear.format(mdateTo)

            }
            sum = 0.0F
            listNew = ArrayList<TurnoverRes>()
            statisticsPresenter.validCheck(SplashScreenActivity.token, TurnoverReq(dateFrom,dateTo))
        }
    }

    fun setToolbar() {
        var toolbar = binding.toolbarStatistics
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun getFilter(list: List<TurnoverRes>) {
        adapter.setData(list)
        binding.rcvStatistics.adapter = adapter
        binding.rcvStatistics.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        val sum_money = SplashScreenActivity.formatter.format(sum).toString() + " đ"
        println(sum)
        binding.sumMoney.setText(sum_money)
    }

    override fun Empty() {
        dialog.showStartDialog3(getString(R.string.error_empty2),this)
    }

    override fun DateError() {
        dialog.showStartDialog3(getString(R.string.errorDate3),this)
    }
}