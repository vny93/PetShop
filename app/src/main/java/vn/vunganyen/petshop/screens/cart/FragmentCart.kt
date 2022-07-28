package vn.vunganyen.petshop.screens.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.data.adapter.AdapterCartDetail
import vn.vunganyen.petshop.data.model.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.cartDetail.getListCartDetail.CartDetailSpRes
import vn.vunganyen.petshop.data.model.cartDetail.post.CartDetailRes2
import vn.vunganyen.petshop.databinding.FragmentCartBinding
import vn.vunganyen.petshop.screens.home.HomeActivity
import vn.vunganyen.petshop.screens.login.LoginActivity

class FragmentCart : Fragment(), CartInterface {
    lateinit var binding: FragmentCartBinding
    lateinit var cartPresenter: CartPresenter
    var adapter : AdapterCartDetail = AdapterCartDetail()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        cartPresenter = CartPresenter(this)
        checkToken(HomeActivity.token)
        setEvent()
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
     //   var req = CartStatusReq("", HomeActivity.profile.result.makh)
        var req = CartStatusReq("", "KH1")
        cartPresenter.getCartByStatus(HomeActivity.token,req)
    }

    fun setEvent(){
        binding.btnCartLogin.setOnClickListener{
            var intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getListSuccess(list: List<CartDetailSpRes>) {
        println("Có list rồi")
        adapter.setData(list)
        binding.rcvMyCard.adapter = adapter
        binding.rcvMyCard.layoutManager =  LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
    }

    override fun cartEmpty() {
        println("Giỏ hàng chưa có sản phẩm")
    }

}