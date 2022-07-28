package vn.vunganyen.petshop.screens.productDetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.classSupport.StartAlertDialog
import vn.vunganyen.petshop.data.model.proDetail.ProDetailReq
import vn.vunganyen.petshop.data.model.proDetail.ProDetailRes
import vn.vunganyen.petshop.databinding.ActivityProductDetailBinding
import vn.vunganyen.petshop.screens.home.HomeActivity
import vn.vunganyen.petshop.screens.login.LoginActivity
import java.text.DecimalFormat

class ProDetailActivity : AppCompatActivity(), ProDetailInterface {
    lateinit var binding: ActivityProductDetailBinding
    lateinit var proDetailPresenter: ProDetailPresenter
    var dialog: StartAlertDialog = StartAlertDialog()
    var soluong = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        proDetailPresenter = ProDetailPresenter(this)
        getData()
        setEvent()
    }

    fun getData() {
        var id = getIntent().getStringExtra("id").toString()
        var req = ProDetailReq(id)
        proDetailPresenter.getProDetail(req)
    }

    @SuppressLint("StringFormatMatches")
    fun setEvent() {
        binding.backProductDetail.setOnClickListener {
            finish()
        }

        binding.minus.setOnClickListener {
            var number = binding.edtNumber.text.toString().toInt()
            if (number > 1) {
                number = number - 1
                binding.edtNumber.setText(number.toString())
            }
        }
        binding.add.setOnClickListener {
            var number = binding.edtNumber.text.toString().toInt()
            number = number + 1
            binding.edtNumber.setText(number.toString())
        }

        binding.btnAddCart.setOnClickListener {
            var number = binding.edtNumber.text.toString().toInt()
            if (number == 0) {
                dialog.showStartDialog3(getString(R.string.tv_numError), this)
            } else if (number > soluong) {
                dialog.showStartDialog2(getString(R.string.tv_numProDetail, soluong), this)
            } else {
                //check token để đưa qua trang login
                // lưu lại vị trí đứng để login vô lại trang đó
                // chú ý: chỉ có vài vị trí cần login : chi tiết sản phẩm khi thêm vào card , fragment card, account
                if (HomeActivity.token.equals("")) {
                    dialog.showStartDialog4(getString(R.string.proDeltologin), this)
                    dialog.clickOk = { ->
                        var intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    println("gọi api status nè")
                    var req = CartStatusReq("", HomeActivity.profile.result.makh)
                    proDetailPresenter.getCartByStatus(HomeActivity.token, req)
                }
            }
        }

        binding.layoutProdetail.setOnClickListener {
            binding.edtNumber.clearFocus()
            binding.layoutProdetail.hideKeyboard()
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
        soluong = res.soluong
        if (soluong == 0) {
            binding.tvNumberPro.setText(getString(R.string.out_of_stock))
            binding.btnAddCart.setBackground(resources.getDrawable(R.color.gray))
            binding.btnAddCart.setTextColor(resources.getColor(R.color.black))
            binding.edtNumber.setText("")
            binding.btnAddCart.isEnabled = false
//            binding.btnUpdataProfile.isEnabled = false
        } else binding.tvNumberPro.setText(res.soluong.toString())

    }

    fun View.hideKeyboard(): Boolean {
        try {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        } catch (ignored: RuntimeException) {
        }
        return false
    }
}