package vn.vunganyen.petshop.screens.admin.inputData.mngProductType.listPT

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.adapter.admin.AdapterPTMng
import vn.vunganyen.petshop.data.model.admin.productType.checkPTUse.CheckPTReq
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.data.model.client.productType.ProductTypeRes
import vn.vunganyen.petshop.databinding.ActivityProTypeMngBinding
import vn.vunganyen.petshop.screens.admin.inputData.mngBrand.insertBrand.InsertBrandActivity
import vn.vunganyen.petshop.screens.admin.inputData.mngProductType.custom.CustomPTMngActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class ProTypeMngActivity : AppCompatActivity(), ProTypeMngInterface {
    lateinit var binding : ActivityProTypeMngBinding
    lateinit var proTypeMngPresenter: ProTypeMngPresenter
    var adapter : AdapterPTMng = AdapterPTMng()
    var dialog: StartAlertDialog = StartAlertDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProTypeMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        proTypeMngPresenter = ProTypeMngPresenter(this)
        getData()
        setEvent()
        setToolbar()
        callDialogInvoke()
    }

    fun getData(){
        proTypeMngPresenter.getList()
    }

    fun setEvent(){
        binding.imvInsertPT.setOnClickListener{
            val intent = Intent(this, CustomPTMngActivity::class.java)
            intent.putExtra("type","insert")
            startActivity(intent)
        }
    }

    fun callDialogInvoke(){
        adapter.click = {
                data ->
            println("maloaisp:"+data.maloaisp)
            proTypeMngPresenter.checkUse(SplashScreenActivity.token, CheckPTReq(data.maloaisp))
        }
    }

    override fun GetList(list: List<ProductTypeRes>) {
        adapter.setData(list)
        binding.rcvListPT.adapter = adapter
        binding.rcvListPT.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    override fun RemoveFail() {
        dialog.showStartDialog3(getString(R.string.RemovePTFail),this)
    }

    override fun AllowRemove(id: String) {
        dialog.showStartDialog4(getString(R.string.AllowRemovePT),this)
        dialog.clickOk = {
            ->proTypeMngPresenter.removePT(SplashScreenActivity.token, CheckPTReq(id))
        }
    }

    override fun RemoveSuccess() {
        proTypeMngPresenter.getList()
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
        proTypeMngPresenter.getList()
    }
}