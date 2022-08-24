package vn.vunganyen.petshop.screens.admin.inputData.mngProductType.listPT

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
import vn.vunganyen.petshop.data.adapter.admin.AdapterPTMng
import vn.vunganyen.petshop.data.model.admin.productType.checkPTUse.CheckPTReq
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffRes
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
    companion object{
        var listPT = ArrayList<ProductTypeRes>()
        lateinit var listFilter : ArrayList<ProductTypeRes>
    }
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

        binding.edtSearchPT.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                var str = binding.edtSearchPT.text.toString()
                proTypeMngPresenter.getFilter(str)
            }
        })

        binding.viewPtMng.setOnClickListener{
            binding.edtSearchPT.clearFocus()
            binding.viewPtMng.hideKeyboard()
        }
        binding.rcvListPT.setOnClickListener{
            binding.edtSearchPT.clearFocus()
            binding.rcvListPT.hideKeyboard()
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
        proTypeMngPresenter.getList()
        binding.edtSearchPT.setText("")
        binding.edtSearchPT.clearFocus()
    }
}