package vn.vunganyen.petshop.screens.client.brand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.databinding.ActivityBrandDetailBinding

class BrandDetailActivity : AppCompatActivity(), BrandDelInterface {
    lateinit var binding : ActivityBrandDetailBinding
    lateinit var branDelPresenter: BranDelPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrandDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        branDelPresenter = BranDelPresenter(this)
        getData()
        setEvent()
    }

    fun getData(){
        var idBrand = getIntent().getStringExtra("idBrand").toString()
        var req = BrandDetailReq(idBrand)
        branDelPresenter.getBranDetail(req)
    }

    fun setEvent(){
        binding.backBrandDetail.setOnClickListener{
            finish()
        }
    }

    override fun getBrandSuccess(res: BrandDetailRes) {
        if (res.logo != null) {
            val strUrl: List<String> = res.logo.split("3000/")
            var url = PathApi.BASE_URL + strUrl.get(1)
            Picasso.get().load(url).into(binding.imvBrandDetail)
        }
        binding.phoneBrand.setText(res.sdt)
        binding.emailBrand.setText(res.email)
        if(res.mota != null){
            binding.tvBodyBrand.setText(res.mota)
        }
        val title = getString(R.string.tv_brand)+" "+ res.tenhang
        binding.nameBrand.setText(title)
    }
}