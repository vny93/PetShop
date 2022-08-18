package vn.vunganyen.petshop.screens.admin.inputData.mngBrand.listBrand

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.admin.AdapterBrandMng
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailReq
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityBrandMngBinding
import vn.vunganyen.petshop.screens.admin.inputData.mngBrand.insertBrand.InsertBrandActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class BrandMngActivity : AppCompatActivity(), BrandMngInterface {
    lateinit var binding : ActivityBrandMngBinding
    lateinit var brandMngPresenter: BrandMngPresenter
    var adapter : AdapterBrandMng = AdapterBrandMng()
    var dialog: StartAlertDialog = StartAlertDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrandMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        brandMngPresenter = BrandMngPresenter(this)
        getData()
        setEvent()
        setToolbar()
        callDialogInvoke()
    }

    fun getData(){
        brandMngPresenter.getListBrand()
    }

    fun setEvent(){
        binding.imvInsertBrand.setOnClickListener{
            val intent = Intent(this,InsertBrandActivity::class.java)
            startActivity(intent)
        }
    }

    fun callDialogInvoke(){
        adapter.click = {
                data ->
            println("mahang:"+data.mahang)
            brandMngPresenter.checkBrandUse(SplashScreenActivity.token, BrandDetailReq(data.mahang))
          //  cartPresenter.removeCartDetail(SplashScreenActivity.token,req)
        }
    }

    override fun GetListBrand(list: List<BrandDetailRes>) {
        adapter.setData(list)
        binding.rcvListBrand.adapter = adapter
        binding.rcvListBrand.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    override fun RemoveFail() {
        dialog.showStartDialog3(getString(R.string.RemoveBrandFail),this)
    }

    override fun AllowRemove(id : String) {
        dialog.showStartDialog4(getString(R.string.AllRemoveBrand),this)
        dialog.clickOk = {
            ->brandMngPresenter.removeBrand(SplashScreenActivity.token,BrandDetailReq(id))
        }
    }

    override fun RemoveSuccess() {
        brandMngPresenter.getListBrand()
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
        brandMngPresenter.getListBrand()
    }
}