package vn.vunganyen.petshop.screens.productDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.proDetail.ProDetailReq
import vn.vunganyen.petshop.data.model.proDetail.ProDetailRes
import vn.vunganyen.petshop.databinding.ActivityProductDetailBinding
import java.text.DecimalFormat

class ProDetailActivity : AppCompatActivity(), ProDetailInterface {
    lateinit var binding : ActivityProductDetailBinding
    lateinit var proDetailPresenter: ProDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        proDetailPresenter = ProDetailPresenter(this)
        getData()
        setEvent()
    }

    fun getData(){
        var id  = getIntent().getStringExtra("id").toString()
        var req = ProDetailReq(id)
        proDetailPresenter.getProDetail(req)
    }

    fun setEvent(){
        binding.backProductDetail.setOnClickListener{
            finish()
        }

        var number = binding.edtNumber.text.toString().toInt()
        println("number: "+number)
        binding.minus.setOnClickListener{
            if(number > 1){
                number = number - 1
                println("number: "+number)
                binding.edtNumber.setText(number.toString())
            }
        }
        binding.add.setOnClickListener{
            number = number + 1
            println("number: "+number)
            binding.edtNumber.setText(number.toString())
        }
    }

    override fun getDetailSuccess(res: ProDetailRes, res2: BrandDetailRes) {
        if (res.hinhanh != null) {
            val strUrl: List<String> = res.hinhanh.split("3000/")
            var url = PathApi.BASE_URL + strUrl.get(1)
            Picasso.get().load(url).into(binding.imvProDetail)
        }
        binding.nameProDetail.setText(res.tensp)
        val formatter = DecimalFormat("###,###,###") //định nghĩa 1 lần rồi xài nhiều lần
        val price = formatter.format(res.gia.toInt()).toString() + " đ"
        binding.tvPrice.setText(price)
        binding.brand.setText(res2.tenhang)
        binding.tvBody.setText(res.mota)

    }
}