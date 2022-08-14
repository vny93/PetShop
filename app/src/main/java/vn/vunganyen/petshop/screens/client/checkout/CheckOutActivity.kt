package vn.vunganyen.petshop.screens.client.checkout

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
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
import vn.vunganyen.petshop.data.model.client.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.client.cart.userUpdate.UserUpdateReq
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetCDSpRes
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityCheckOutBinding
import vn.vunganyen.petshop.databinding.DialogPaypalBinding
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.util.*

class CheckOutActivity : AppCompatActivity(), CheckOutInterface {
    lateinit var binding : ActivityCheckOutBinding
    lateinit var checkOutPresenter : CheckOutPresenter
    lateinit var bindingDialog : DialogPaypalBinding
    lateinit var dialog: Dialog
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var alertDialog: StartAlertDialog = StartAlertDialog()
    var adapter : AdapterProductCheckout = AdapterProductCheckout()
    var magh = 0
    var value = ""
    companion object{
        lateinit var listCartDetail : List<GetCDSpRes>
        var WAIT_STATUS = "Chờ xác nhận"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this@CheckOutActivity)
        checkOutPresenter = CheckOutPresenter(this)
        setViewDate()
        getData()
        setEvent()
        value = String.format("%.2f", exchangeRate(SplashScreenActivity.sumPrice))
    }

    fun exchangeRate(price : Float): Float{
        var VND = 23390
        return price/VND
    }

    fun setViewDate(){
        binding.edtDateReceive.setText("" + day + "/" + (month + 1) + "/" + year)
        val strSumPrice = SplashScreenActivity.formatter.format(SplashScreenActivity.sumPrice).toString() + " đ"
        println(strSumPrice)
        binding.sumCartMoney2.setText(strSumPrice)
        binding.edtNameReceive.setText(SplashScreenActivity.profileClient.result.hoten)
        binding.edtPhoneReceive.setText(SplashScreenActivity.profileClient.result.sdt)
        binding.edtEmailReceive.setText(SplashScreenActivity.profileClient.result.email)
        binding.edtAddressReceive.setText(SplashScreenActivity.profileClient.result.diachi)
    }


    fun getData(){
        magh = getIntent().getIntExtra("magh",0)
        var req = GetCartReq(magh)
        checkOutPresenter.getListCartDetail(SplashScreenActivity.token,req)
    }

    fun setEvent(){
        binding.imvCalendar.setOnClickListener {
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

        binding.lnlCheckout.setOnClickListener{
            clearFocus()
        }

        binding.lnlCheckout2.setOnClickListener{
            clearFocus()
        }

        binding.backCheckout.setOnClickListener{
            finish()
        }
        binding.btnCheckOut2.setOnClickListener{
            var name = binding.edtNameReceive.text.toString()
            var phone = binding.edtPhoneReceive.text.toString()
            var email = binding.edtEmailReceive.text.toString()
            var address = binding.edtAddressReceive.text.toString()
        //    var status = "Chờ xác nhận"
            var expectedDate = binding.edtDateReceive.text.toString()
            var mdate : Date = SplashScreenActivity.formatdate1.parse(expectedDate)
            var strDate = SplashScreenActivity.formatdate.format(mdate)
            var req = UserUpdateReq(name,address,phone,email,
                SplashScreenActivity.sumPrice,WAIT_STATUS,strDate,magh)
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
                //    Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
                //    Toast.makeText(this,"Thanh toán thành công", Toast.LENGTH_SHORT).show()
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

    fun clearFocus(){
        binding.edtEmailReceive.clearFocus()
        binding.edtPhoneReceive.clearFocus()
        binding.edtNameReceive.clearFocus()
        binding.edtAddressReceive.clearFocus()
        binding.lnlCheckout.hideKeyboard()
        binding.lnlCheckout2.hideKeyboard()
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

    override fun DateError() {
        alertDialog.showStartDialog3(getString(R.string.errorDate),this)
    }

    override fun ValidCheckSuccess(req : UserUpdateReq) {
        ShowDialog(Gravity.CENTER,req,value)
    }
}