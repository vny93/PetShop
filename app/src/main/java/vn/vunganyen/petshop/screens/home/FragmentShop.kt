package vn.vunganyen.petshop.screens.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.AdapterPhoto
import vn.vunganyen.petshop.data.model.classSupport.Photo
import vn.vunganyen.petshop.databinding.FragmentShopBinding
import java.util.*
import kotlin.concurrent.timerTask

class FragmentShop : Fragment() {
    lateinit var binding : FragmentShopBinding
    lateinit var photoAdapter : AdapterPhoto
    lateinit var listPhoto : List<Photo>
    var time : Timer = Timer()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentShopBinding.inflate(inflater,container,false)
        listPhoto = getListData()
        photoAdapter = AdapterPhoto(requireContext().applicationContext,listPhoto)
        binding.viewPager.adapter = photoAdapter
        binding.circleIndicator.setViewPager(binding.viewPager)
        autoSlideImage()
        return binding.root
    }

    fun autoSlideImage(){
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

}