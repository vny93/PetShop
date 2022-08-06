package vn.vunganyen.petshop.screens.checkout

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
import vn.vunganyen.petshop.BuildConfig
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.AdapterProductCheckout
import vn.vunganyen.petshop.data.model.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.cart.userUpdate.UserUpdateReq
import vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail.GetCDSpRes
import vn.vunganyen.petshop.data.model.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityCheckOutBinding
import vn.vunganyen.petshop.databinding.DialogPaypalBinding
import vn.vunganyen.petshop.screens.home.HomeActivity

class CheckOutActivity : AppCompatActivity(), CheckOutInterface {
    lateinit var binding : ActivityCheckOutBinding
    lateinit var checkOutPresenter : CheckOutPresenter
    lateinit var bindingDialog : DialogPaypalBinding
    lateinit var dialog: Dialog
    var alertDialog: StartAlertDialog = StartAlertDialog()
    var adapter : AdapterProductCheckout = AdapterProductCheckout()
    var magh = 0
    var value = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this@CheckOutActivity)
        checkOutPresenter = CheckOutPresenter(this)
        setViewDate()
        getData()
        setEvent()
        value = String.format("%.2f", exchangeRate(HomeActivity.sumPrice))
    }

    fun exchangeRate(price : Float): Float{
        var VND = 23390
        println("chia ra bao nhiêu v: "+price/VND)
        return price/VND
    }

    fun setViewDate(){
        val strSumPrice = HomeActivity.formatter.format(HomeActivity.sumPrice).toString() + " đ"
        println(strSumPrice)
        binding.sumCartMoney2.setText(strSumPrice)
        binding.edtNameReceive.setText(HomeActivity.profile.result.hoten)
        binding.edtPhoneReceive.setText(HomeActivity.profile.result.sdt)
        binding.edtEmailReceive.setText(HomeActivity.profile.result.email)
        binding.edtAddressReceive.setText(HomeActivity.profile.result.diachi)
    }


    fun getData(){
        magh = getIntent().getIntExtra("magh",0)
        var req = GetCartReq(magh)
        checkOutPresenter.getListCartDetail(HomeActivity.token,req)
    }

    fun setEvent(){
        binding.backCheckout.setOnClickListener{
            finish()
        }
        binding.btnCheckOut2.setOnClickListener{
            var name = binding.edtNameReceive.text.toString()
            var phone = binding.edtPhoneReceive.text.toString()
            var email = binding.edtEmailReceive.text.toString()
            var address = binding.edtAddressReceive.text.toString()
            var status = "Đang chờ duyệt"
            var req = UserUpdateReq(name,address,phone,email,HomeActivity.sumPrice,status,magh)
            checkOutPresenter.validCheck(req)
        }
    }

    fun setPaypal(dialog : Dialog, binding: DialogPaypalBinding, req: UserUpdateReq, value : String){
        val config = CheckoutConfig(
            application = application,
            clientId = "AQAy_7xYsSpfwRmu5nYZpHikNm871t5Mx1sBgq-u262CSiOKhUnuotaK_wOsBmtUY38Ly5kEbr-fEhIt",
            environment = Environment.SANDBOX,
            String.format("%s://paypalpay", "vn.vunganyen.petshop"),
            currencyCode = CurrencyCode.USD,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = true
            )
        )
        BuildConfig.APPLICATION_ID
        PayPalCheckout.setConfig(config)
        binding.payPalButton.setup(
            createOrder =
            CreateOrder { createOrderActions ->
                val order =
                    Order(
                        intent = OrderIntent.CAPTURE,
                        appContext = AppContext(userAction = UserAction.PAY_NOW),
                        purchaseUnitList =
                        listOf(
                            PurchaseUnit(
                                amount =
                                Amount(currencyCode = CurrencyCode.USD, value = value)
                            )
                        )
                    )
                createOrderActions.create(order)
                dialog.dismiss()
            },
            onApprove =
            OnApprove { approval ->
                approval.orderActions.capture { captureOrderResult ->
                    Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
                    Toast.makeText(this,"Thanh toán thành công", Toast.LENGTH_SHORT).show()
                    //xử lí lưu thông tin và thông báo thanh toán thành công!
                    checkOutPresenter.userUpdateCart(req)
                }
            }
        )
    }

    fun ShowDialog(gravity: Int, req: UserUpdateReq, value : String){
        bindingDialog = DialogPaypalBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)
        setPaypal(dialog,bindingDialog, req,value)
        val window = dialog.window ?: return
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
//        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val windowAttributes = window.attributes
        windowAttributes.gravity = gravity
        window.attributes = windowAttributes

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(false)
        } else {
            dialog.setCancelable(false)
        }
        bindingDialog.cancelDialog.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun getListSuccess(list: List<GetCDSpRes>) {
        adapter.setData(list)
        binding.rcvCheckout.adapter = adapter
        binding.rcvCheckout.layoutManager =  LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
    }

    override fun PhoneIllegal() {
        alertDialog.showStartDialog3(getString(R.string.PhoneIllegal),this)
    }

    override fun EmailIllegal() {
        alertDialog.showStartDialog3(getString(R.string.EmailIllegal),this)
    }

    override fun Empty() {
        alertDialog.showStartDialog3(getString(R.string.error_empty),this)
    }

    override fun UpdateSuccess() {
//        var intent = Intent(this, CheckoutSuccess::class.java)
//        startActivity(intent)
        alertDialog.showStartDialog2(getString(R.string.tv_checkout_success),this)
        alertDialog.clickOk ={
            -> finish()
        }
    }

    override fun UpdateError() {
        alertDialog.showStartDialog3(getString(R.string.tv_checkout_fail),this)
    }

    override fun ValidCheckSuccess(req : UserUpdateReq) {
        ShowDialog(Gravity.CENTER,req,value)
    }
}