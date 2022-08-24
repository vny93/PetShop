package vn.vunganyen.petshop.screens.admin.orderShip.shipperListOrder

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.admin.AdapterOrderShipper
import vn.vunganyen.petshop.data.model.admin.cart.getOrderShipper.ShipperGetOrderReq
import vn.vunganyen.petshop.data.model.client.cart.add.AddCartRes
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityShipperOrderBinding
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.util.*

class ShipperOrderActivity : AppCompatActivity(), ShipperOrderInterface {
    lateinit var binding : ActivityShipperOrderBinding
    lateinit var shipperOrderPresenter: ShipperOrderPresenter
    var adapter : AdapterOrderShipper = AdapterOrderShipper()
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var dialog: StartAlertDialog = StartAlertDialog()
    var status = ""
    var makh = 0
    var dateFrom = ""
    var dateTo = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShipperOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shipperOrderPresenter = ShipperOrderPresenter(this)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
        setAdapterSpinner()
        var req = ShipperGetOrderReq(SplashScreenActivity.profileStaff.result.manv,dateFrom,dateTo,status)
        shipperOrderPresenter.filterOrderShipper(SplashScreenActivity.token,req)
    }

    fun setEvent(){
        binding.imvDateFrom.setOnClickListener {
            val dpd =
                this?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            binding.edtDateFrom.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
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
                            binding.edtDateTo.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
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

        binding.spinnerStatus.setOnItemClickListener(({adapterView, view, i , l ->
            status = adapterView.getItemAtPosition(i).toString()
            var req = ShipperGetOrderReq(SplashScreenActivity.profileStaff.result.manv,dateFrom,dateTo,status)
            shipperOrderPresenter.filterOrderShipper(SplashScreenActivity.token,req)
        }))

        binding.edtDateFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                val mdate : Date = SplashScreenActivity.formatdate1.parse(binding.edtDateFrom.text.toString())
                dateFrom = SplashScreenActivity.formatdate.format(mdate)
                println("dateFrom: "+dateFrom)
                var req = ShipperGetOrderReq(SplashScreenActivity.profileStaff.result.manv,dateFrom,dateTo,status)
                shipperOrderPresenter.filterOrderShipper(SplashScreenActivity.token,req)
            }
        })

        binding.edtDateTo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {

                val mdate : Date = SplashScreenActivity.formatdate1.parse(binding.edtDateTo.text.toString())
                dateTo = SplashScreenActivity.formatdate.format(mdate)
                println("dateFrom: "+dateTo)
                var req = ShipperGetOrderReq(SplashScreenActivity.profileStaff.result.manv,dateFrom,dateTo,status)
                shipperOrderPresenter.filterOrderShipper(SplashScreenActivity.token,req)
            }
        })
    }

    fun setAdapterSpinner(){
        var list = resources.getStringArray(R.array.status_shipper)
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerStatus.setAdapter(adapter)
        binding.spinnerStatus.setText(binding.spinnerStatus.adapter.getItem(0).toString(), false)
        status = binding.spinnerStatus.adapter.getItem(0).toString()
    }

    fun View.hideKeyboard(): Boolean {
        try {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        } catch (ignored: RuntimeException) {
        }
        return false
    }

    fun setToolbar() {
        var toolbar = binding.toolbarListOrder
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun getList(list: List<AddCartRes>) {
        adapter.setData(list)
        binding.rcvListOrderShipper.adapter = adapter
        binding.rcvListOrderShipper.layoutManager =  LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
    }

    override fun onResume() {
        super.onResume()
        var req = ShipperGetOrderReq(SplashScreenActivity.profileStaff.result.manv,dateFrom,dateTo,status)
        shipperOrderPresenter.filterOrderShipper(SplashScreenActivity.token,req)
    }

}