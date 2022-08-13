package vn.vunganyen.petshop.screens.client.myOrder.transport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.data.adapter.AdapterOrder
import vn.vunganyen.petshop.data.model.cart.add.AddCartRes
import vn.vunganyen.petshop.data.model.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.databinding.FragmentTransportBinding
import vn.vunganyen.petshop.screens.client.home.main.HomeActivity

class TransportFragment : Fragment(), TransportInterface {
    lateinit var binding : FragmentTransportBinding
    lateinit var transportPresenter: TransportPresenter
    var adapter : AdapterOrder = AdapterOrder()
    var status = "Chờ lấy hàng"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransportBinding.inflate(inflater,container,false)
        transportPresenter = TransportPresenter(this)
        getData()
        return binding.root
    }

    fun getData(){
        var req = CartStatusReq(status, HomeActivity.profile.result.makh)
        transportPresenter.getCartByStatus(HomeActivity.token,req)
    }

    override fun getListSuccess(list: List<AddCartRes>) {
        adapter.setData(list)
        binding.rcvMyTransOrder.adapter = adapter
        binding.rcvMyTransOrder.layoutManager =  LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
    }

    override fun getEmpty() {
        TODO("Not yet implemented")
    }

}