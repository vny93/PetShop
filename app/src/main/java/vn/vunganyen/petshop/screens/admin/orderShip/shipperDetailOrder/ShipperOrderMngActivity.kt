package vn.vunganyen.petshop.screens.admin.orderShip.shipperDetailOrder

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.admin.cart.shipperUpdate.ShipperUpdateOrderReq
import vn.vunganyen.petshop.data.model.admin.staff.getDetail.PostDetailStaffReq
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.client.cart.add.AddCartRes
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityShipperOrderMngBinding
import vn.vunganyen.petshop.screens.client.myOrderDetail.OrderDetailActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.util.*

class ShipperOrderMngActivity : AppCompatActivity(), ShipperOrderMngInterface {
    lateinit var binding : ActivityShipperOrderMngBinding
    lateinit var shipperOrderMngPresenter: ShipperOrderMngPresenter
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var data : AddCartRes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShipperOrderMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shipperOrderMngPresenter = ShipperOrderMngPresenter(this)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
        data = getIntent().getSerializableExtra("data") as AddCartRes
        binding.tvIdCart.setText(data.magh.toString())
        var mdate : Date = SplashScreenActivity.formatdate2.parse(data.ngaydat)
        var strDate = SplashScreenActivity.formatdate3.format(mdate)
        binding.tvBookDate.setText(strDate)

        var mdate2 : Date = SplashScreenActivity.formatdate2.parse(data.ngaydukien)
        var cal2 = Calendar.getInstance()
        cal2.time = mdate2
        cal2.add(Calendar.DATE, 1) // number of days to add
        var strDate2 = SplashScreenActivity.formatdate1.format(cal2.time)
        binding.edtDateReceive.setText(strDate2)

        var date : Date = SplashScreenActivity.formatdate.parse(data.ngaygiao)
        c.time = date
        c.add(Calendar.DATE, 1) // number of days to add
        var strDate3 = SplashScreenActivity.formatdate4.format(c.time)
        binding.edtDate.setText(strDate3)

        val price = SplashScreenActivity.formatter.format(data.tongtien).toString() + " đ"
        binding.tvPriceOrder.setText(price)

        shipperOrderMngPresenter.getStaffReview(SplashScreenActivity.token, PostDetailStaffReq(data.manvduyet))
        shipperOrderMngPresenter.getStaffShipper(SplashScreenActivity.token, PostDetailStaffReq(data.manvgiao))
        binding.tvStatus.setText(data.trangthai)
        binding.imvDateReceive.isEnabled = false

        if(data.trangthai.equals(SplashScreenActivity.DELIVERED)){
            binding.btnSave.visibility = View.GONE
            binding.btnDelivered.visibility = View.GONE
        }
    }

    fun setEvent(){
        binding.btnOrderDetail.setOnClickListener{
            var intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("magh",data.magh)
            startActivity(intent)
        }

        binding.imvDateReceive.setOnClickListener {
            val dpd =
                this?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            binding.edtDateReceive.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
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


        binding.btnSave.setOnClickListener{
            if(binding.btnSave.text.toString().equals(getString(R.string.tv_save))){
                binding.btnSave.setText(getString(R.string.pf_btnSave))

                binding.btnDelivered.visibility = View.GONE
                binding.imvDateReceive.isEnabled = true
                binding.cartDateReceive.setCardBackgroundColor(Color.WHITE)
                binding.edtDateReceive.setBackground(resources.getDrawable(vn.vunganyen.petshop.R.color.white))
            }
            else{
                val dateReceive = binding.edtDateReceive.text.toString()
                val mdate : Date = SplashScreenActivity.formatdate1.parse(dateReceive)
                var strDate = SplashScreenActivity.formatdate.format(mdate)
                println("magh: " + data.magh)
                println("ngaydukien:" + strDate)
                println("trangthai:" + data.trangthai)
                var req = ShipperUpdateOrderReq(data.magh,data.trangthai,strDate)
                shipperOrderMngPresenter.validCheckUpdate(SplashScreenActivity.token,req)
            }
        }
        binding.btnDelivered.setOnClickListener{
       //     dialog.showStartDialog4(getString(R.string.message_cancelOrder), this)
       //     dialog.clickOk = { ->
            val dateReceive = binding.edtDateReceive.text.toString()
            val mdate : Date = SplashScreenActivity.formatdate1.parse(dateReceive)
            var strDate = SplashScreenActivity.formatdate.format(mdate)
            println("magh: " + data.magh)
            println("ngaydukien:" + dateReceive)
            println("trangthai:" + data.trangthai)
            var req = ShipperUpdateOrderReq(data.magh,SplashScreenActivity.DELIVERED,strDate)
            shipperOrderMngPresenter.validCheckUpdate(SplashScreenActivity.token,req)
          //      }
        }
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

    override fun getReview(res: MainStaffRes) {
        binding.tvNameReview.setText(res.result.hoten)
    }

    override fun getShipper(res: MainStaffRes) {
        binding.tvStaff.setText(res.result.hoten)
    }

    override fun UpdateSucces() {
        dialog.showStartDialog3(getString(R.string.UpdateBrandSuccess),this)
        if(binding.btnSave.text.toString().equals(getString(R.string.pf_btnSave))){
            binding.btnSave.setText(getString(R.string.tv_save))

            binding.btnDelivered.visibility = View.VISIBLE

            binding.imvDateReceive.isEnabled = false
            binding.cartDateReceive.setCardBackgroundColor(Color.parseColor("#EFEDED"))
            binding.edtDateReceive.setBackground(resources.getDrawable(R.color.gray))
        }
        else{
            binding.btnSave.visibility = View.GONE
            binding.btnDelivered.visibility = View.GONE
            binding.tvStatus.setText(SplashScreenActivity.DELIVERED)
        }

    }

    override fun UpdateError() {
        dialog.showStartDialog3(getString(R.string.UpdateBrandError),this)
    }


    override fun DateError() {
        dialog.showStartDialog3(getString(R.string.errorDate),this)
    }

}