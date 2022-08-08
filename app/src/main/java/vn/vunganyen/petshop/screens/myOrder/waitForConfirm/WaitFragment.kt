package vn.vunganyen.petshop.screens.myOrder.waitForConfirm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.data.adapter.AdapterCart
import vn.vunganyen.petshop.data.model.cart.add.AddCartRes
import vn.vunganyen.petshop.data.model.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.databinding.FragmentWaitBinding
import vn.vunganyen.petshop.screens.home.HomeActivity


class WaitFragment : Fragment(), WaitInterface {
    lateinit var binding : FragmentWaitBinding
    lateinit var waitPresenter : WaitPresenter
    var adapter : AdapterCart = AdapterCart()
    var status = "Chờ xác nhận"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWaitBinding.inflate(inflater,container,false)
        waitPresenter = WaitPresenter(this)
        getData()
        return binding.root
    }

    fun getData(){
        var req = CartStatusReq(status, HomeActivity.profile.result.makh)
        waitPresenter.getCartByStatus(HomeActivity.token,req)
    }

    override fun getListSuccess(list: List<AddCartRes>) {
        adapter.setData(list)
        binding.rcvMyWaitOrder.adapter = adapter
        binding.rcvMyWaitOrder.layoutManager =  LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
    }

    override fun getEmpty() {
        TODO("Not yet implemented")
    }
}