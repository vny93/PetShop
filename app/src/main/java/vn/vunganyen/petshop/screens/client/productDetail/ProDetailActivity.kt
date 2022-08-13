package vn.vunganyen.petshop.screens.client.productDetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.cart.getByStatus.CartStatusReq
import vn.vunganyen.petshop.data.model.cartDetail.post.PostCDReq
import vn.vunganyen.petshop.data.model.cartDetail.update.PutCDReq
import vn.vunganyen.petshop.data.model.classSupport.StartAlertDialog
import vn.vunganyen.petshop.data.model.proDetail.ProDetailReq
import vn.vunganyen.petshop.data.model.proDetail.ProDetailRes
import vn.vunganyen.petshop.databinding.ActivityProductDetailBinding
import vn.vunganyen.petshop.screens.client.brand.BrandDetailActivity
import vn.vunganyen.petshop.screens.client.home.main.HomeActivity
import vn.vunganyen.petshop.screens.client.login.LoginActivity

class ProDetailActivity : AppCompatActivity(), ProDetailInterface {
    lateinit var binding: ActivityProductDetailBinding
    lateinit var proDetailPresenter: ProDetailPresenter
    var dialog: StartAlertDialog = StartAlertDialog()
    var soluong = 0
    var idBrand = ""
    lateinit var reqAddCartDetail : PostCDReq

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

        binding.brand.setOnClickListener{
            var intent = Intent(this, BrandDetailActivity::class.java)
            intent.putExtra("idBrand",idBrand)
            startActivity(intent)
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
                dialog.showStartDialog3(getString(R.string.tv_numProDetail, soluong), this)
            } else {
                //check token để đưa qua trang login
                // lưu lại vị trí đứng để login vô lại trang đó
                // chú ý: chỉ có vài vị trí cần login : chi tiết sản phẩm khi thêm vào card , fragment card, account
                if (HomeActivity.token.equals("")) {
                    dialog.showStartDialog4(getString(R.string.login_required), this)
                    dialog.clickOk = { ->
                        var intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    println("gọi api status nè")
                    var req = CartStatusReq("", HomeActivity.profile.result.makh)
                    reqAddCartDetail.ctsoluong = number
                    proDetailPresenter.getCartByStatus(HomeActivity.token, req, reqAddCartDetail, soluong)
                }
            }
        }

        binding.layoutProdetail.setOnClickListener {
            binding.edtNumber.clearFocus()
            binding.layoutProdetail.hideKeyboard()
        }
    }

    fun updateDataAdapter(data : PutCDReq){ //bên Adapter gọi
        proDetailPresenter = ProDetailPresenter(this)
        proDetailPresenter.updateCartDetail(HomeActivity.token,data)

    }


    override fun getDetailSuccess(res: ProDetailRes, res2: BrandDetailRes) {
        idBrand = res.mahang
        if (res.hinhanh != null) {
            val strUrl: List<String> = res.hinhanh.split("3000/")
            var url = PathApi.BASE_URL + strUrl.get(1)
            Picasso.get().load(url).into(binding.imvProDetail)
        }
        binding.nameProDetail.setText(res.tensp)
        if(res.giagiam == res.gia){
            val price = HomeActivity.formatter.format(res.gia.toInt()).toString() + " đ"
            binding.tvPrice.setText(price)
        }
        else{
            val price = HomeActivity.formatter.format(res.gia.toInt()).toString() + " đ"
            val priceDiscount = HomeActivity.formatter.format(res.giagiam.toInt()).toString() + " đ"
            val spanned = SpannableString(price)
            spanned.setSpan(StrikethroughSpan(), 0, price.length, 0)
            binding.tvDiscount.setText(spanned)
            binding.tvPrice.setText(priceDiscount)
        }

        binding.brand.setText(res2.tenhang)
        if(res.mota != null){
            binding.tvBody.setText(res.mota)
        }
        soluong = res.soluong
        if (soluong == 0) {
            binding.tvNumberPro.setText(getString(R.string.out_of_stock))
            binding.btnAddCart.setBackground(resources.getDrawable(R.color.gray))
            binding.btnAddCart.setTextColor(resources.getColor(R.color.black))
            binding.edtNumber.setText("")
            binding.btnAddCart.isEnabled = false
//            binding.btnUpdataProfile.isEnabled = false
        } else binding.tvNumberPro.setText(res.soluong.toString())

        //gán gia gốc hay giá giảm đều được, vì nếu giảm thì cũng gán giá giảm và k giảm thì giá giảm cũng = giá gốc
        reqAddCartDetail = PostCDReq(0,res.masp,res.giagiam,0) // gán trước những thuộc tính cần thiết
    }

    override fun addCDsuccess() {
        dialog.showStartDialog3(getString(R.string.add_CDsuccess), this)
    }

    override fun inventNum() {
        dialog.showStartDialog3(getString(R.string.inventNum), this)
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