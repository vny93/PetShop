package vn.vunganyen.petshop.screens.admin.inputData.mngProduct.listProduct

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.admin.AdapterProductMng
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductReq
import vn.vunganyen.petshop.data.model.admin.product.getList.ProductOriginalRes
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityProductMngBinding
import vn.vunganyen.petshop.screens.admin.inputData.mngProduct.customProduct.CustomPMngActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class ProductMngActivity : AppCompatActivity(),ProductMngInterface {
    lateinit var binding : ActivityProductMngBinding
    lateinit var productMngPresenter: ProductMngPresenter
    var adapter : AdapterProductMng= AdapterProductMng()
    var dialog: StartAlertDialog = StartAlertDialog()
    companion object{
        var listProduct = ArrayList<ProductOriginalRes>()
        lateinit var listFilter : ArrayList<ProductOriginalRes>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productMngPresenter = ProductMngPresenter(this)
        getData()
        setEvent()
        setToolbar()
        callDialogInvoke()
    }

    fun getData(){
        productMngPresenter.getList(SplashScreenActivity.token)
    }

    fun setEvent(){
        binding.imvInsertP.setOnClickListener{
            val intent = Intent(this, CustomPMngActivity::class.java)
            intent.putExtra("type","insert")
            startActivity(intent)
        }
        binding.edtSearchProduct.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                var str = binding.edtSearchProduct.text.toString()
                productMngPresenter.getFilter(str)
            }
        })


        binding.viewProduct.setOnClickListener{
            binding.edtSearchProduct.clearFocus()
            binding.viewProduct.hideKeyboard()
        }
        binding.rcvListP.setOnClickListener{
            binding.edtSearchProduct.clearFocus()
            binding.rcvListP.hideKeyboard()
        }
    }

    fun callDialogInvoke(){
        adapter.click = {
                data ->
            println("maloaisp:"+data.maloaisp)
            productMngPresenter.checkUse(SplashScreenActivity.token, CheckProductReq(data.masp))
        }
    }

    override fun GetList(list: List<ProductOriginalRes>) {
        adapter.setData(list)
        binding.rcvListP.adapter = adapter
        binding.rcvListP.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    override fun RemoveFail() {
        dialog.showStartDialog3(getString(R.string.RemovePFail),this)
    }

    override fun AllowRemove(id: String) {
        dialog.showStartDialog4(getString(R.string.AllowRemoveP),this)
        dialog.clickOk = {
            ->productMngPresenter.remove(SplashScreenActivity.token, CheckProductReq(id))
        }
    }

    override fun RemoveSuccess() {
        productMngPresenter.getList(SplashScreenActivity.token)
        dialog.showStartDialog3(getString(R.string.RemoveBrandSuccess),this)
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

    fun setToolbar() {
        var toolbar = binding.toolbarListBrand
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun onResume() {
        super.onResume()
        productMngPresenter.getList(SplashScreenActivity.token)
        binding.edtSearchProduct.setText("")
        binding.edtSearchProduct.clearFocus()
    }
}