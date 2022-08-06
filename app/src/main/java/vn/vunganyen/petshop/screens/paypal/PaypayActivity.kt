package vn.vunganyen.petshop.screens.paypal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import vn.vunganyen.petshop.databinding.ActivityPaypayBinding

class PaypayActivity : AppCompatActivity() {
    lateinit var binding : ActivityPaypayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaypayBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                                Amount(currencyCode = CurrencyCode.USD, value = "10.00")
                            )
                        )
                    )
                createOrderActions.create(order)
            },
            onApprove =
            OnApprove { approval ->
                approval.orderActions.capture { captureOrderResult ->
                    Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
                    Toast.makeText(this,"Thanh toán thành công",Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

}