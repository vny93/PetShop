package vn.vunganyen.petshop.screens.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.vunganyen.petshop.data.adapter.AdapterProductType
import vn.vunganyen.petshop.data.model.productType.ProductTypeRes
import vn.vunganyen.petshop.databinding.FragmentExploreBinding


class FragmentExplore : Fragment(), ExploreInterface {
    lateinit var binding : FragmentExploreBinding
    lateinit var explorePresenter: ExplorePresenter
    var adapter : AdapterProductType = AdapterProductType()
    companion object{
        lateinit var listProductType : ArrayList<ProductTypeRes>
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentExploreBinding.inflate(inflater,container,false)
        getData()
        return binding.root
    }

    fun getData(){
        explorePresenter = ExplorePresenter(this)
        explorePresenter.getList()

    }

    override fun getListSuccess(list: List<ProductTypeRes>) {
        adapter.setData(list)
        binding.rcvProType.adapter = adapter
    }

}