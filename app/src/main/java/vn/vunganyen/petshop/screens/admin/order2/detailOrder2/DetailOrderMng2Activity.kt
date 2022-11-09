package vn.vunganyen.petshop.screens.admin.order2.detailOrder2

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.admin.cart.adminUpdate.UpdateStatusReq
import vn.vunganyen.petshop.data.model.admin.staff.getDetail.PostDetailStaffReq
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.MainStaffRes
import vn.vunganyen.petshop.data.model.client.cart.add.AddCartRes
import vn.vunganyen.petshop.data.model.client.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.client.cart.userUpdate.UserUpdateReq
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.data.model.fastDelivery.detailStatus.DetailStatusReq
import vn.vunganyen.petshop.data.model.fastDelivery.order.SendOrderReq
import vn.vunganyen.petshop.databinding.ActivityDetailOrderMng2Binding
import vn.vunganyen.petshop.databinding.DialogPaypalBinding
import vn.vunganyen.petshop.databinding.DialogSendOrderBinding
import vn.vunganyen.petshop.screens.client.myOrderDetail.OrderDetailActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailOrderMng2Activity : AppCompatActivity(), DetailOrder2Interface {
    lateinit var binding : ActivityDetailOrderMng2Binding
    lateinit var detailOrder2Presenter: DetailOrder2Presenter
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var dialog2: Dialog
    lateinit var bindingDialog : DialogSendOrderBinding
    lateinit var data : AddCartRes
    var status =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderMng2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        detailOrder2Presenter = DetailOrder2Presenter(this)
        dialog2 = Dialog(this@DetailOrderMng2Activity)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
        data = getIntent().getSerializableExtra("data") as AddCartRes
        if(data.trangthai.equals(SplashScreenActivity.PENDING)){ //chờ duyệt
            status = SplashScreenActivity.DELIVERED //đang giao
            binding.btnSave.setText("Gửi hàng")
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
        binding.tvMass.setText(data.khoiluong.toString() + " kg")
        binding.tvDateReceive.setText(strDate2)
        val price = SplashScreenActivity.formatter.format(data.tongtien).toString() + " đ"
        binding.tvPriceOrder.setText(price)
        // binding.tvStatus.setText(data.trangthai)
        binding.tvStatus.setText(data.trangthai)
        if(data.manvduyet != null){
            detailOrder2Presenter.getStaffReview(SplashScreenActivity.token, PostDetailStaffReq(data.manvduyet))
        }
    }

    fun setEvent(){
        binding.btnOrderDetail.setOnClickListener{
            var intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("magh",data.magh)
            startActivity(intent)
        }

        binding.btnSave.setOnClickListener{
            if (binding.btnSave.text.toString().equals("Gửi hàng")){
                ShowDialog(Gravity.CENTER)
//                var idShop = "CH001"
//                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
//                val currentDate = sdf.format(Date())
//
//                var sizeOrder = binding.edtSize.text.toString()
//                var noteOrder = ""
//                if(!binding.edtNote.text.toString().isEmpty()){
//                    noteOrder = binding.edtNote.text.toString()
//                }
//                var req = SendOrderReq(data.magh,data.hotennguoinhan,data.diachinguoinhan,data.sdtnguoinhan,
//                data.ptthanhtoan,data.tongtien,data.ttthanhtoan,data.khoiluong,sizeOrder,data.phigiao,
//                data.htvanchuyen,noteOrder,currentDate,idShop)
//                //call API gửi hàng
//                detailOrder2Presenter.checkEmpty(sizeOrder,req,GetCartReq(data.magh))
            }
        }

        binding.btnCencal.setOnClickListener{
            println("call hủy đơn hàng nè")
            dialog.showStartDialog4(getString(R.string.message_cancelOrder), this)
            dialog.clickOk = { ->
                var req = UpdateStatusReq(data.magh,SplashScreenActivity.CANCELLED,SplashScreenActivity.profileAdmin.result.manv)
                detailOrder2Presenter.updateStatus(SplashScreenActivity.token,req,1)
            }
        }

    }

    fun ShowDialog(gravity: Int){
        bindingDialog = DialogSendOrderBinding.inflate(layoutInflater)
        dialog2.setContentView(bindingDialog.root)

        val window = dialog2.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
//        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window.attributes
        windowAttributes.gravity = gravity
        window.attributes = windowAttributes

        if (Gravity.CENTER == gravity) {
            dialog2.setCancelable(false)
        } else {
            dialog2.setCancelable(false)
        }
        bindingDialog.sendOrder.setOnClickListener{
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val currentDate = sdf.format(Date())

            var sizeOrder = bindingDialog.edtSize.text.toString()
            var noteOrder = ""
            if(!bindingDialog.edtNote.text.toString().isEmpty()){
                noteOrder = bindingDialog.edtNote.text.toString()
            }
            var req = SendOrderReq(data.magh,data.hotennguoinhan,data.diachinguoinhan,data.sdtnguoinhan,
                data.ptthanhtoan,data.tongtien,data.ttthanhtoan,data.khoiluong,sizeOrder,data.phigiao,
                data.htvanchuyen,noteOrder,currentDate,SplashScreenActivity.IDSHOP)
            //call API gửi hàng
            dialog2.dismiss()
            detailOrder2Presenter.checkEmpty(sizeOrder,req,GetCartReq(data.magh))
        }

        bindingDialog.cancelDialog.setOnClickListener{
            dialog2.dismiss()
        }
        bindingDialog.dialogSend.setOnClickListener{
            bindingDialog.edtSize.clearFocus()
            bindingDialog.edtNote.clearFocus()
            bindingDialog.dialogSend.hideKeyboard()
        }
        dialog2.show()
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

    fun setToolbar(){
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

    override fun UpdateStatusSucces() {
        binding.tvStatus.setText(SplashScreenActivity.DELIVERY)
        binding.tvNameReview.setText(SplashScreenActivity.profileAdmin.result.hoten)
    }

    override fun Empty() {
        dialog.showStartDialog3(getString(R.string.empty_size),this)
    }

    override fun CancelOrderSuccess() {
        dialog.showStartDialog3(getString(R.string.cancel_order_success),this)
        binding.btnSave.visibility = View.GONE
        binding.btnCencal.visibility = View.GONE
        binding.tvStatus.setText(SplashScreenActivity.CANCELLED)
        binding.tvNameReview.setText(SplashScreenActivity.profileAdmin.result.hoten)
        Handler().postDelayed({
            detailOrder2Presenter.getListCartDetail(SplashScreenActivity.token, GetCartReq(data.magh))
        }, 3000)
    }

    override fun sendOrderSuccess() {
        dialog.showStartDialog3(getString(R.string.send_order_success),this)
        binding.btnCencal.visibility = View.GONE
        binding.btnSave.visibility = View.GONE
        println("qua đây")
        detailOrder2Presenter.insertDetailStatus(DetailStatusReq(data.magh,1,"",""))

    }

    override fun addDetailStatus() {
        var req = UpdateStatusReq(data.magh,SplashScreenActivity.DELIVERY,SplashScreenActivity.profileAdmin.result.manv)
        detailOrder2Presenter.updateStatus(SplashScreenActivity.token,req,2)
    }
}