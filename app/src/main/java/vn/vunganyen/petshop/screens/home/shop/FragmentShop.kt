package vn.vunganyen.petshop.screens.home.shop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.*
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.classSupport.Photo
import vn.vunganyen.petshop.data.model.product.get.ProductRes
import vn.vunganyen.petshop.databinding.FragmentShopBinding
import vn.vunganyen.petshop.screens.home.seeAllProduct.SeeAllActivity
import vn.vunganyen.petshop.screens.search.SearchProActivity
import java.util.*
import kotlin.concurrent.timerTask

class FragmentShop : Fragment(), ShopInterface {
    lateinit var binding : FragmentShopBinding
    lateinit var shopPresenter: ShopPresenter
    lateinit var photoAdapter : AdapterPhoto
    lateinit var listPhoto : List<Photo>
    var time : Timer = Timer()
    var adapterDiscount : AdapterDiscount = AdapterDiscount()
    var adapterNew : AdapterIsNew = AdapterIsNew()
    var adapterGood : AdapterIsGood = AdapterIsGood()
    var adapterBrand : AdapterBrand = AdapterBrand()
    companion object{
        lateinit var listDiscount : List<ProductRes>
        lateinit var listIsNew : List<ProductRes>
        lateinit var listIsGood : List<ProductRes>
        lateinit var listBrand : List<BrandDetailRes>
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentShopBinding.inflate(inflater,container,false)
        shopPresenter = ShopPresenter(this)
        autoSlideImage()
        setEvent()
        getData()
        return binding.root
    }

    fun getData(){
        shopPresenter.getListDiscount()
    }

    fun autoSlideImage(){
        listPhoto = getListData()
        photoAdapter = AdapterPhoto(requireContext().applicationContext,listPhoto)
        binding.viewPager.adapter = photoAdapter
        binding.circleIndicator.setViewPager(binding.viewPager)
        if(listPhoto == null || listPhoto.isEmpty() || binding.viewPager == null){
            return
        }
        if(time == null){
            time = Timer()
        }
        time.schedule(timerTask {
            Handler(Looper.getMainLooper()).post(Runnable(){
                var currenItem = binding.viewPager.currentItem
                var totalItem = listPhoto.size-1
                if(currenItem < totalItem){
                    currenItem++
                    binding.viewPager.setCurrentItem(currenItem)
                }else{
                    binding.viewPager.setCurrentItem(0)
                }
            })
        }, 500,3000)
    }

    fun setEvent(){
        binding.tvSeeAllDiscount.setOnClickListener{
            var intent = Intent(context, SeeAllActivity::class.java)
            intent.putExtra("type",1)
            startActivity(intent)
        }
        binding.tvSeeAllNew.setOnClickListener{
            var intent = Intent(context, SeeAllActivity::class.java)
            intent.putExtra("type",2)
            startActivity(intent)
        }
        binding.tvSeeAllGood.setOnClickListener{
            var intent = Intent(context, SeeAllActivity::class.java)
            intent.putExtra("type",3)
            startActivity(intent)
        }
        binding.edtSearchProduct.setOnClickListener{
            var intent = Intent(context, SearchProActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(time != null){
            time.cancel()
        }
    }

    fun getListData(): List<Photo>{
        var list = ArrayList<Photo>()
        list.add(Photo(R.drawable.panner1))
        list.add(Photo(R.drawable.panner2))
        list.add(Photo(R.drawable.panner3))
        list.add(Photo(R.drawable.panner4))
        list.add(Photo(R.drawable.panner5))
        return list
    }

    override fun getListSuccess() {
        adapterDiscount.setData(listDiscount)
        binding.rcvHomeDiscount.adapter = adapterDiscount
        binding.rcvHomeDiscount.layoutManager =  LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)

        adapterNew.setData(listIsNew)
        binding.rcvHomeNew.adapter = adapterNew
        binding.rcvHomeNew.layoutManager =  LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)

        adapterGood.setData(listIsGood)
        binding.rcvHomeGood.adapter = adapterGood
        binding.rcvHomeGood.layoutManager =  LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)

        adapterBrand.setData(listBrand)
        binding.rcvBrand.adapter = adapterBrand
        binding.rcvBrand.layoutManager =  LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)

    }

}