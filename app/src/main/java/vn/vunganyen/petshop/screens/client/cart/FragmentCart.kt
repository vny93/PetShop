package vn.vunganyen.petshop.screens.client.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.AdapterCartDetail
import vn.vunganyen.petshop.data.model.client.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.client.cartDetail.deleteCD.DeleteCDReq
import vn.vunganyen.petshop.data.model.client.cartDetail.getListCartDetail.GetCDSpRes
import vn.vunganyen.petshop.databinding.FragmentCartBinding
import vn.vunganyen.petshop.screens.client.checkout.CheckOutActivity
import vn.vunganyen.petshop.screens.client.login.LoginActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity


class FragmentCart : Fragment(), CartInterface {
    lateinit var binding: FragmentCartBinding
    lateinit var cartPresenter: CartPresenter
    var adapter : AdapterCartDetail = AdapterCartDetail()
    var magh = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        cartPresenter = CartPresenter(this)
        checkToken(SplashScreenActivity.token)
        setEvent()
        callInvoke()
        callDialogInvoke()
        return binding.root
    }

    fun checkToken(token : String){
        if(token.equals("")){
            binding.rcvMyCard.visibility = View.GONE
            binding.btnCheckOut.visibility = View.GONE
            binding.sumCartMoney.visibility = View.GONE
            binding.tvNameMoney.visibility = View.GONE
            binding.tvCartTop.visibility = View.GONE
        }
        else{
            binding.imvCartError.visibility = View.GONE
            binding.tvCartError.visibility = View.GONE
            binding.btnCartLogin.visibility = View.GONE
            getData()
        }
    }

    fun getData(){
        var req = CartStatusReq("", SplashScreenActivity.profileClient.result.makh)
        cartPresenter.getCartByStatus(SplashScreenActivity.token,req)
    }

    fun setEvent(){
        binding.btnCartLogin.setOnClickListener{
            var intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnCheckOut.setOnClickListener{
            var intent = Intent(context, CheckOutActivity::class.java)
            intent.putExtra("magh",magh)
            startActivity(intent)
        }
    }

    fun callInvoke(){
        adapter.click={
            price ->
            println("sum: "+ SplashScreenActivity.sumPrice)
            println("thêm :"+price)
            if(price != null){
                SplashScreenActivity.sumPrice = SplashScreenActivity.sumPrice + price!!
                val strSumPrice = SplashScreenActivity.formatter.format(SplashScreenActivity.sumPrice).toString() + " đ"
                println(strSumPrice)
                binding.sumCartMoney.setText(strSumPrice)
            }
        }
    }

    fun callDialogInvoke(){
        adapter.clickOk = {
            data ->
            println("magh:"+data.magh)
            println("masp:"+data.masp)
            var req = DeleteCDReq(data.magh,data.masp)
            cartPresenter.removeCartDetail(SplashScreenActivity.token,req)
        }
    }


    override fun getListSuccess(list: List<GetCDSpRes>) {
        println("Có list rồi")
        magh = list.get(0).magh
        adapter.setData(list)
        binding.rcvMyCard.adapter = adapter
        binding.rcvMyCard.layoutManager =  LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        binding.btnCheckOut.isEnabled = true
        binding.btnCheckOut.setBackground(resources.getDrawable(R.drawable.custom_button))
    }

    override fun cartEmpty() {
        binding.rcvMyCard.visibility = View.GONE
        binding.sumCartMoney.setText(getString(R.string.sum_price))
        binding.tvCartError.visibility = View.VISIBLE
        binding.tvCartError.setText(getString(R.string.tv_cartEmpty))
        binding.btnCheckOut.isEnabled = false
        binding.btnCheckOut.setBackground(resources.getDrawable(R.drawable.custom_button_false))
    }

    override fun onResume() {
        super.onResume()
        if(!SplashScreenActivity.token.equals("")){
            getData()
        }
    }

    override fun deleteSuccess() {
        getData()
    }
}