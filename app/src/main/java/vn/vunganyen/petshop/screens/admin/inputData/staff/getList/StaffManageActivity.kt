package vn.vunganyen.petshop.screens.admin.inputData.staff.getList

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
import vn.vunganyen.petshop.data.adapter.admin.AdapterStaffMng
import vn.vunganyen.petshop.data.model.admin.product.checkProductUse.CheckProductReq
import vn.vunganyen.petshop.data.model.admin.staff.getDetail.PostDetailStaffReq
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffRes
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.data.model.client.product.search.SearchProductReq
import vn.vunganyen.petshop.databinding.ActivityStaffManageBinding
import vn.vunganyen.petshop.screens.admin.inputData.mngProduct.customProduct.CustomPMngActivity
import vn.vunganyen.petshop.screens.admin.inputData.staff.customStaff.CustomStaffMngActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity

class StaffManageActivity : AppCompatActivity(),StaffMngInterface {
    lateinit var binding : ActivityStaffManageBinding
    lateinit var staffMngPresenter: StaffMngPresenter
    var adapter : AdapterStaffMng = AdapterStaffMng()
    var dialog: StartAlertDialog = StartAlertDialog()
    companion object{
       var listStaff = ArrayList<StaffRes>()
       lateinit var listFilter : ArrayList<StaffRes>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaffManageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        staffMngPresenter = StaffMngPresenter(this)
        getData()
        setEvent()
        setToolbar()
        callDialogInvoke()
    }

    fun getData(){
        staffMngPresenter.getList(SplashScreenActivity.token)
    }

    fun setEvent(){
        binding.imvInsertP.setOnClickListener{
            val intent = Intent(this, CustomStaffMngActivity::class.java)
            intent.putExtra("type","insert")
            startActivity(intent)
        }
        binding.edtSearchStaff.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                var str = binding.edtSearchStaff.text.toString()
                staffMngPresenter.getFilter(str)
            }
        })

        binding.viewStaffMng.setOnClickListener{
            binding.edtSearchStaff.clearFocus()
            binding.viewStaffMng.hideKeyboard()
        }
        binding.rcvListStaff.setOnClickListener{
            binding.edtSearchStaff.clearFocus()
            binding.rcvListStaff.hideKeyboard()
        }
    }

    fun callDialogInvoke(){
        adapter.click = {
                data ->
            println("maloaisp:"+data.manv)
            staffMngPresenter.checkStaff(SplashScreenActivity.token, PostDetailStaffReq(data.manv))
        }
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
        staffMngPresenter.getList(SplashScreenActivity.token)
        binding.edtSearchStaff.setText("")
        binding.edtSearchStaff.clearFocus()
    }

    override fun GetList(list: List<StaffRes>) {
        adapter.setData(list)
        binding.rcvListStaff.adapter = adapter
        binding.rcvListStaff.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    override fun RemoveFail() {
        dialog.showStartDialog3(getString(R.string.RemoveStaffFail),this)
    }

    override fun AllowRemove(id: String) {
        dialog.showStartDialog4(getString(R.string.AllowRemoveStaff),this)
        dialog.clickOk = {
            ->staffMngPresenter.remove(SplashScreenActivity.token, PostDetailStaffReq(id))
        }
    }

    override fun RemoveSuccess() {
        staffMngPresenter.getList(SplashScreenActivity.token)
        dialog.showStartDialog3(getString(R.string.RemoveBrandSuccess),this)
    }

}