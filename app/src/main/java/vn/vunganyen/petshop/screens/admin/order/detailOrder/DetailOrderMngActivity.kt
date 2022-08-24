package vn.vunganyen.petshop.screens.admin.order.detailOrder

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.admin.cart.adminUpdate.AdminUpdateOrderReq
import vn.vunganyen.petshop.data.model.admin.cart.adminUpdate.UpdateStatusReq
import vn.vunganyen.petshop.data.model.admin.cart.getOrder.GetOrderReq
import vn.vunganyen.petshop.data.model.admin.cart.getStaffName.GetStaffNameReq
import vn.vunganyen.petshop.data.model.admin.cart.getStaffName.GetStaffNameRes
import vn.vunganyen.petshop.data.model.admin.staff.getDetail.PostDetailStaffReq
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.client.cart.add.AddCartRes
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityDetailOrderMngBinding
import vn.vunganyen.petshop.screens.client.myOrderDetail.OrderDetailActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.util.*
import kotlin.collections.ArrayList

class DetailOrderMngActivity : AppCompatActivity(),DetailOrderInterface {
    lateinit var binding : ActivityDetailOrderMngBinding
    lateinit var detailOrderPresenter: DetailOrderPresenter
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var data : AddCartRes
    var idShipper = ""
    var status =""
    var listShipper = ArrayList<GetStaffNameRes>()
    var listName = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailOrderPresenter = DetailOrderPresenter(this)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
        data = getIntent().getSerializableExtra("data") as AddCartRes
        detailOrderPresenter.getListShipper(SplashScreenActivity.token, GetStaffNameReq(SplashScreenActivity.SHIPPER))
        if(data.trangthai.equals(SplashScreenActivity.PENDING)){ //chờ duyệt
            status = SplashScreenActivity.DELIVERY //đang giao
            binding.btnSave.setText("Duyệt đơn")
        }
        else if(data.trangthai.equals(SplashScreenActivity.CANCELLED) ||
            data.trangthai.equals(SplashScreenActivity.DELIVERED)){ //đã hủy + đã giao
            binding.btnSave.visibility = View.GONE
            binding.btnCencal.visibility = View.GONE
        }
        else { //đang giao
            status = data.trangthai
            binding.btnCencal.visibility = View.GONE
        }
        binding.tvIdCart.setText(data.magh.toString())
        var mdate : Date = SplashScreenActivity.formatdate2.parse(data.ngaydat)
        var strDate = SplashScreenActivity.formatdate3.format(mdate)
        binding.tvBookDate.setText(strDate)
        var mdate2 : Date = SplashScreenActivity.formatdate2.parse(data.ngaydukien)
        var cal2 = Calendar.getInstance()
        cal2.time = mdate2
        cal2.add(Calendar.DATE, 1) // number of days to add
        var strDate2 = SplashScreenActivity.formatdate4.format(cal2.time)

        binding.tvDateReceive.setText(strDate2)
        val price = SplashScreenActivity.formatter.format(data.tongtien).toString() + " đ"
        binding.tvPriceOrder.setText(price)
       // binding.tvStatus.setText(data.trangthai)
        binding.tvStatus.setText(data.trangthai)
        if(data.ngaygiao != null){
            var date : Date = SplashScreenActivity.formatdate.parse(data.ngaygiao)
            c.time = date
            c.add(Calendar.DATE, 1) // number of days to add
            var strDate = SplashScreenActivity.formatdate1.format(c.time)
            binding.edtDate.setText(strDate)
        }
        if(data.manvduyet != null){
            detailOrderPresenter.getStaffReview(SplashScreenActivity.token, PostDetailStaffReq(data.manvduyet))
        }
        binding.imvDate.isEnabled = false
        binding.cartSpinnerStaff.setCardBackgroundColor(Color.parseColor("#EFEDED"))

    }

    fun setEvent(){
        binding.btnOrderDetail.setOnClickListener{
            var intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("magh",data.magh)
            startActivity(intent)
        }

        binding.imvDate.setOnClickListener {
            val dpd =
                this?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            binding.edtDate.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
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


        binding.spinnerStaff.setOnItemClickListener(({adapterView, view, i , l ->
            for(list in listShipper){
                if(list.hoten.equals(adapterView.getItemAtPosition(i).toString())){
                    idShipper = list.manv
                }
            }
        }))

        binding.btnSave.setOnClickListener {
            if (binding.btnSave.text.toString().equals("Duyệt đơn") || binding.btnSave.text.toString().equals("Cập nhật")) {
                binding.btnSave.setText(getString(R.string.pf_btnSave))
                setAdapterShipper(listName)

                binding.cartSpinnerStaff.setCardBackgroundColor(Color.WHITE)
                binding.spinnerStaff.setBackground(resources.getDrawable(R.color.white))

                binding.imvDate.isEnabled = true
                binding.cartDateShip.setCardBackgroundColor(Color.WHITE)
                binding.edtDate.setBackground(resources.getDrawable(R.color.white))

                binding.btnOrderDetail.visibility = View.GONE
                binding.btnCencal.visibility = View.GONE
            } else {
                var dateShip = binding.edtDate.text.toString()
                var strDate = ""
                if (!dateShip.equals("")) {
                    val mdate: Date = SplashScreenActivity.formatdate1.parse(dateShip)
                    strDate = SplashScreenActivity.formatdate.format(mdate)
                }
                var idReview = ""
                if(data.manvduyet != null){
                    idReview = data.manvduyet
                }
                else idReview = SplashScreenActivity.profileAdmin.result.manv
                println("magh: " + data.magh)
                println("ngaygiao:" + strDate)
                println("trangthai:" + status)
                println("mnvduyet:" + idReview)
                println("mnvgiao:" +idShipper)
                var req = AdminUpdateOrderReq(data.magh,strDate,status,idReview,idShipper)
                detailOrderPresenter.validCheckUpdate(SplashScreenActivity.token,req)
            }
        }

        binding.btnCencal.setOnClickListener{
            println("call hủy đơn hàng nè")
            dialog.showStartDialog4(getString(R.string.message_cancelOrder), this)
            dialog.clickOk = { ->
                var req = UpdateStatusReq(data.magh,SplashScreenActivity.CANCELLED,SplashScreenActivity.profileAdmin.result.manv)
                detailOrderPresenter.updateStatus(SplashScreenActivity.token,req)
            }
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

    fun setSpinnerPT(){
        for(list in listShipper){
            if(data.manvgiao.equals(list.manv)){
                binding.spinnerStaff.setText(list.hoten,false)
            }
        }
    }

    fun setAdapterShipper(list : List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerStaff.setAdapter(adapter)
        binding.spinnerStaff.setHint("Nhân viên giao")
    }

    override fun getListShipper(list: List<GetStaffNameRes>) {
        listShipper = list as ArrayList<GetStaffNameRes>
        for(i in 0.. list.size-1){
            listName.add(list.get(i).hoten)
        }
        if(data.manvgiao != null){
            setSpinnerPT()
        }
    }

    override fun getReview(res: MainStaffRes) {
        binding.tvNameReview.setText(res.result.hoten)
    }

    override fun UpdateSucces() {
        dialog.showStartDialog3(getString(R.string.UpdateBrandSuccess),this)
        binding.btnSave.setText(getString(R.string.tv_save))
        binding.btnOrderDetail.visibility = View.VISIBLE
        binding.btnCencal.visibility = View.GONE
        binding.cartSpinnerStaff.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.spinnerStaff.setBackground(resources.getDrawable(R.color.gray))

        binding.imvDate.isEnabled = false
        binding.cartDateShip.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtDate.setBackground(resources.getDrawable(R.color.gray))
        val mlist = mutableListOf("")
        mlist.clear()
        setAdapterShipper(mlist)
    }

    override fun UpdateError() {
        dialog.showStartDialog3(getString(R.string.UpdateBrandError),this)
    }

    override fun Empty() {
        dialog.showStartDialog3(getString(R.string.error_empty),this)
    }

    override fun DateError() {
        dialog.showStartDialog3(getString(R.string.errorDateShip),this)
    }

    override fun CancelOrderSuccess() {
        dialog.showStartDialog3(getString(R.string.cancel_order_success),this)
        binding.btnSave.visibility = View.GONE
        binding.btnCencal.visibility = View.GONE
        binding.tvStatus.setText(SplashScreenActivity.CANCELLED)
        binding.tvNameReview.setText(SplashScreenActivity.profileAdmin.result.hoten)
    }
}