package vn.vunganyen.petshop.screens.admin.inputData.mngProduct.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.admin.AdapterProductMng
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductReq
import vn.vunganyen.petshop.data.model.admin.product.getList.ProductOriginalRes
import vn.vunganyen.petshop.data.model.admin.productType.checkPTUse.CheckPTReq
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityProductMngBinding
import vn.vunganyen.petshop.screens.admin.inputData.mngBrand.insertBrand.InsertBrandActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class ProductMngActivity : AppCompatActivity(),ProductMngInterface {
    lateinit var binding : ActivityProductMngBinding
    lateinit var productMngPresenter: ProductMngPresenter
    var adapter : AdapterProductMng= AdapterProductMng()
    var dialog: StartAlertDialog = StartAlertDialog()
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
            val intent = Intent(this, InsertBrandActivity::class.java)
            startActivity(intent)
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
    }
}