package vn.vunganyen.petshop.screens.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.AdapterCartDetail
import vn.vunganyen.petshop.data.model.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.cartDetail.deleteCD.DeleteCDReq
import vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail.GetCDSpRes
import vn.vunganyen.petshop.databinding.FragmentCartBinding
import vn.vunganyen.petshop.screens.home.HomeActivity
import vn.vunganyen.petshop.screens.login.LoginActivity
import java.text.DecimalFormat


class FragmentCart : Fragment(), CartInterface {
    lateinit var binding: FragmentCartBinding
    lateinit var cartPresenter: CartPresenter
    var adapter : AdapterCartDetail = AdapterCartDetail()
    val formatter = DecimalFormat("###,###,###")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        cartPresenter = CartPresenter(this)
        checkToken(HomeActivity.token)
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
        }
        else{
            binding.imvCartError.visibility = View.GONE
            binding.tvCartError.visibility = View.GONE
            binding.btnCartLogin.visibility = View.GONE
            getData()
        }
    }

    fun getData(){
        var req = CartStatusReq("", HomeActivity.profile.result.makh)
        cartPresenter.getCartByStatus(HomeActivity.token,req)
    }

    fun setEvent(){
        binding.btnCartLogin.setOnClickListener{
            var intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun callInvoke(){
        adapter.click={
            price ->
            println("sum: "+HomeActivity.sumPrice)
            println("thêm :"+price)
            if(price != null){
                HomeActivity.sumPrice = HomeActivity.sumPrice + price!!
                val strSumPrice = formatter.format(HomeActivity.sumPrice).toString() + " đ"
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
            cartPresenter.removeCartDetail(HomeActivity.token,req)
        }
    }


    override fun getListSuccess(list: List<GetCDSpRes>) {
        println("Có list rồi")
        adapter.setData(list)
        binding.rcvMyCard.adapter = adapter
        binding.rcvMyCard.layoutManager =  LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
    }

    override fun cartEmpty() {
        binding.tvCartError.visibility = View.VISIBLE
        binding.tvCartError.setText("Giỏ hàng chưa có sản phẩm")
    }

    override fun deleteSuccess() {
        getData()
    }
}