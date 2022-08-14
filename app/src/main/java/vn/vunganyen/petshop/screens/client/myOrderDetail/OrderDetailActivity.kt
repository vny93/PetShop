package vn.vunganyen.petshop.screens.client.myOrderDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.AdapterProductCheckout
import vn.vunganyen.petshop.data.model.client.cart.getCart.GetCartReq
import vn.vunganyen.petshop.data.model.client.cart.getCart.GetCartRes
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetCDSpRes
import vn.vunganyen.petshop.databinding.ActivityOrderDetailBinding
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.util.*

class OrderDetailActivity : AppCompatActivity(), OrderDetailInterface {
    lateinit var binding : ActivityOrderDetailBinding
    lateinit var orderDetailPresenter: OrderDetailPresenter
    var adapter : AdapterProductCheckout = AdapterProductCheckout()
    companion object{
       lateinit var inforOrder : GetCartRes
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        orderDetailPresenter = OrderDetailPresenter(this)
        getData()
        setEvent()
    }

    fun getData(){
        var magh = getIntent().getIntExtra("magh",0)
        var req = GetCartReq(magh)
        orderDetailPresenter.get(SplashScreenActivity.token,req)
    }

    fun setEvent(){
        binding.backOrderDetail.setOnClickListener{
            finish()
        }
    }

    override fun GetListSuccess(list: List<GetCDSpRes>) {
        var infor = inforOrder.hotennguoinhan +"\n"+
                    inforOrder.sdtnguoinhan +"\n"+
                    inforOrder.emailnguoinhan +"\n"+
                    inforOrder.diachinguoinhan +"\n"
        binding.edtInforOrder.setText(infor)
        val strSumPrice = SplashScreenActivity.formatter.format(inforOrder.tongtien).toString() + " đ"
        binding.sumMoneyOrder.setText(strSumPrice)
        adapter.setData(list)
        binding.rcvOrderDetail.adapter = adapter
        binding.rcvOrderDetail.layoutManager =  LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        binding.idCart.setText(inforOrder.magh.toString())
        var timeOrder  :Date = SplashScreenActivity.formatdate2.parse(inforOrder.ngaydat)
        var intendTime  :Date = SplashScreenActivity.formatdate2.parse(inforOrder.ngaydukien)
        binding.timeOrder.setText(SplashScreenActivity.formatdate3.format(timeOrder))
        binding.intendTime.setText(SplashScreenActivity.formatdate4.format(intendTime))
        if(inforOrder.ngaygiao != null){
            var deliveryTime  :Date = SplashScreenActivity.formatdate2.parse(inforOrder.ngaygiao)
            binding.deliveryTime.setText(SplashScreenActivity.formatdate3.format(deliveryTime))
        }
    }

    override fun ErrorGetCart() {
        Toast.makeText(this,getString(R.string.error_getCart),Toast.LENGTH_SHORT).show()
    }

    override fun ErrorGetList() {
        Toast.makeText(this,getString(R.string.error_getList),Toast.LENGTH_SHORT).show()
    }
}