package vn.vunganyen.petshop.screens.admin.inputData.staff.customStaff

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffRes
import vn.vunganyen.petshop.data.model.admin.staff.updateProfile.PutStaffReq
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.data.model.client.register.addAuth.AddAuthReq
import vn.vunganyen.petshop.data.model.client.register.findAuth.FindAuthReq
import vn.vunganyen.petshop.data.model.client.register.findRole.FindRoleRes
import vn.vunganyen.petshop.databinding.ActivityCustomStaffMngBinding
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.util.*

class CustomStaffMngActivity : AppCompatActivity(),CustomStaffInterface {
    lateinit var binding : ActivityCustomStaffMngBinding
    lateinit var customStaffPresenter: CustomStaffPresenter
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var dialog: StartAlertDialog = StartAlertDialog()
    var listRole = ArrayList<FindRoleRes>()
    var listRoleName = ArrayList<String>()
    var roleId = 0
    lateinit var reqStaff : PutStaffReq
    lateinit var reqAuth : AddAuthReq
    companion object{
        lateinit var typeStaff : String
        lateinit var staff : StaffRes
        var roleIdChange = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomStaffMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customStaffPresenter = CustomStaffPresenter(this)
        setData()
        setEvent()
        setToolbar()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setData(){
        binding.edtBirthStaff.setText("" + day + "/" + (month + 1) + "/" + year)
        typeStaff = getIntent().getStringExtra("type") as String
        customStaffPresenter.getListRole(SplashScreenActivity.token)
        if(typeStaff.equals("update")){
            staff = getIntent().getSerializableExtra("data") as StaffRes
            customStaffPresenter.getRoleId(SplashScreenActivity.token, FindAuthReq(staff.tendangnhap))
            binding.titleUsername.visibility = View.GONE
            binding.edtUsernameStaff.visibility = View.GONE
            binding.titlePass.visibility = View.GONE
            binding.hidePass.visibility = View.GONE
            binding.edtPasswordStaff.visibility = View.GONE

            binding.edtStaffId.setText(staff.manv)
            binding.edtNameStaff.setText(staff.hoten)
            binding.edtPhoneStaff.setText(staff.sdt)
            binding.edtEmailStaff.setText(staff.email)
            binding.edtAddressStaff.setText(staff.diachi)
            var date : Date = SplashScreenActivity.formatdate.parse(staff.ngaysinh)
            c.time = date
            c.add(Calendar.DATE, 1) // number of days to add
            var strDate = SplashScreenActivity.formatdate1.format(c.time)
            binding.edtBirthStaff.setText(strDate)
            if(staff.gioitinh.equals("Nam")){
                binding.radioMaleStaff.isChecked = true
            }
            else binding.radioFemaleStaff.isChecked = true
        }
        else{
            binding.toolbarProductDetail.setTitle(getString(R.string.tv_toolbar_insertProduct))
            binding.edtStaffId.isEnabled = true
            binding.cartStaffId.setBackgroundColor(Color.WHITE)
            binding.edtStaffId.setBackground(resources.getDrawable(R.color.white))
            binding.btnSave.setText(getString(R.string.tv_insert))
            binding.cartNameStaff.setCardBackgroundColor(Color.WHITE)
            binding.edtNameStaff.setBackground(resources.getDrawable(R.color.white))
            binding.edtNameStaff.isEnabled = true

            binding.cartBirthStaff.setCardBackgroundColor(Color.WHITE)
            binding.edtBirthStaff.setBackground(resources.getDrawable(R.color.white))

            binding.radioMaleStaff.isEnabled = true
            binding.radioFemaleStaff.isEnabled = true

            binding.cartPhone.setCardBackgroundColor(Color.WHITE)
            binding.edtPhoneStaff.setBackground(resources.getDrawable(R.color.white))
            binding.edtPhoneStaff.isEnabled = true

            binding.cartEmailStaff.setCardBackgroundColor(Color.WHITE)
            binding.edtEmailStaff.setBackground(resources.getDrawable(R.color.white))
            binding.edtEmailStaff.isEnabled = true

            binding.cartAddress.setCardBackgroundColor(Color.WHITE)
            binding.edtAddressStaff.setBackground(resources.getDrawable(R.color.white))
            binding.edtAddressStaff.isEnabled = true

            binding.cartSpinnerRole.setCardBackgroundColor(Color.WHITE)
            binding.spinnerRole.setBackground(resources.getDrawable(R.color.white))
        }
    }


    fun setEvent(){
        binding.imvCalendar.setOnClickListener {
            val dpd =
                this?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            binding.edtBirthStaff.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                            day = mDay
                            month = mMonth
                            year = mYear
                        },
                        year,
                        month,
                        day
                    )
                }
            if (dpd != null) {
                dpd.show()
            }
        }

        binding.spinnerRole.setOnItemClickListener(({adapterView,view,i,l ->
            if(binding.btnSave.text.toString().equals("Cập nhật")){
                for(list in listRole){
                    if(list.tenquyen.equals(adapterView.getItemAtPosition(i).toString())){
                        roleIdChange = list.maquyen
                    }
                }
            }
        }))


        binding.btnSave.setOnClickListener{
            if(binding.btnSave.text.toString().equals("Cập nhật")){
                binding.btnSave.setText(getString(R.string.pf_btnSave))
                setAdapter(listRoleName)
                binding.cartNameStaff.setCardBackgroundColor(Color.WHITE)
                binding.edtNameStaff.setBackground(resources.getDrawable(R.color.white))
                binding.edtNameStaff.isEnabled = true

                binding.cartBirthStaff.setCardBackgroundColor(Color.WHITE)
                binding.edtBirthStaff.setBackground(resources.getDrawable(R.color.white))

                binding.radioMaleStaff.isEnabled = true
                binding.radioFemaleStaff.isEnabled = true

                binding.cartPhone.setCardBackgroundColor(Color.WHITE)
                binding.edtPhoneStaff.setBackground(resources.getDrawable(R.color.white))
                binding.edtPhoneStaff.isEnabled = true

                binding.cartEmailStaff.setCardBackgroundColor(Color.WHITE)
                binding.edtEmailStaff.setBackground(resources.getDrawable(R.color.white))
                binding.edtEmailStaff.isEnabled = true

                binding.cartAddress.setCardBackgroundColor(Color.WHITE)
                binding.edtAddressStaff.setBackground(resources.getDrawable(R.color.white))
                binding.edtAddressStaff.isEnabled = true

                binding.cartSpinnerRole.setCardBackgroundColor(Color.WHITE)
                binding.spinnerRole.setBackground(resources.getDrawable(R.color.white))
            }
            else{
                val id = binding.edtStaffId.text.toString()
                val name = binding.edtNameStaff.text.toString()
                val dateBirth = binding.edtBirthStaff.text.toString()
                val mdate : Date = SplashScreenActivity.formatdate1.parse(dateBirth)
                var strDate = SplashScreenActivity.formatdate.format(mdate)
                var email = binding.edtEmailStaff.text.toString()
                var phone = binding.edtPhoneStaff.text.toString()
                var address = binding.edtAddressStaff.text.toString()
                var gender = ""
                if(binding.radioMaleStaff.isChecked == true){
                    gender = "Nam"
                }
                if(binding.radioFemaleStaff.isChecked == true){
                    gender = "Nữ"
                }
                println("name: "+name)
                println("strDate: "+strDate)
                println("gendar: "+gender)
                println("email: "+email)
                println("phone: "+phone)
                println("address: "+address)
                reqStaff = PutStaffReq(id.toUpperCase(),name,gender,address,strDate,phone,email)
                if(typeStaff.equals("update")){
                    customStaffPresenter.validCheckUpdate(reqStaff)
                }
                else {
                    var username = binding.edtUsernameStaff.text.toString()
                    var pass = binding.edtPasswordStaff.text.toString()
                    reqAuth = AddAuthReq(username,pass,roleIdChange)
                    customStaffPresenter.vailidCheckInsert(reqAuth,reqStaff)
                }
            }
        }

        binding.lnlCustomStaff.setOnClickListener{
            binding.edtUsernameStaff.clearFocus()
            binding.edtPasswordStaff.clearFocus()
            binding.edtNameStaff.clearFocus()
            binding.edtBirthStaff.clearFocus()
            binding.edtPhoneStaff.clearFocus()
            binding.edtEmailStaff.clearFocus()
            binding.edtAddressStaff.clearFocus()
            binding.lnlCustomStaff.hideKeyboard()
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
        var toolbar = binding.toolbarProductDetail
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    override fun getRoleSuccess(list: List<FindRoleRes>) {
        listRole = list as ArrayList<FindRoleRes>
        for(i in 0..list.size-1){
            listRoleName.add(list.get(i).tenquyen)
        }
        if(!binding.btnSave.text.toString().equals("Cập nhật")){
            setAdapter(listRoleName)
        }
    }

    fun setAdapter(list: List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerRole.setAdapter(adapter)
        binding.spinnerRole.setHint("Quyền")
    }

    override fun getRoleId(id: Int) {
        roleId = id
        roleIdChange= id
        println(id)
        for(list in listRole){
            if(roleId.equals(list.maquyen)){
                println(list.maquyen)
                binding.spinnerRole.setText(list.tenquyen,false)
            }
        }
    }

    override fun Empty() {
        dialog.showStartDialog3(getString(R.string.error_empty),this)
    }

    override fun PhoneIllegal() {
        dialog.showStartDialog3(getString(R.string.PhoneIllegal),this)
    }

    override fun EmailIllegal() {
        dialog.showStartDialog3(getString(R.string.EmailIllegal),this)
    }

    override fun PhoneExist() {
        dialog.showStartDialog3(getString(R.string.PhoneExist),this)
    }

    override fun EmailExist() {
        dialog.showStartDialog3(getString(R.string.EmailExist),this)
    }

    override fun AddSucces() {
        dialog.showStartDialog3(getString(R.string.AddAuthSuccess),this)
        binding.edtUsernameStaff.setText("")
        binding.edtPasswordStaff.setText("")
        binding.edtStaffId.setText("")
        binding.edtNameStaff.setText("")
        binding.edtPhoneStaff.setText("")
        binding.edtEmailStaff.setText("")
        binding.edtAddressStaff.setText("")
        binding.spinnerRole.setText("",false)
        binding.radioMaleStaff.setChecked(false)
        binding.radioFemaleStaff.setChecked(false)
    }

    override fun UpdateSucces() {
        dialog.showStartDialog3(getString(R.string.AddProfileSucces),this)
        binding.btnSave.setText(getString(R.string.tv_save))
        binding.cartNameStaff.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtNameStaff.setBackground(resources.getDrawable(R.color.gray))
        binding.edtNameStaff.isEnabled = false

        binding.cartBirthStaff.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtBirthStaff.setBackground(resources.getDrawable(R.color.gray))

        binding.radioMaleStaff.isEnabled = false
        binding.radioFemaleStaff.isEnabled = false

        binding.cartPhone.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtPhoneStaff.setBackground(resources.getDrawable(R.color.gray))
        binding.edtPhoneStaff.isEnabled = false

        binding.cartEmailStaff.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtEmailStaff.setBackground(resources.getDrawable(R.color.gray))
        binding.edtEmailStaff.isEnabled = false

        binding.cartAddress.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtAddressStaff.setBackground(resources.getDrawable(R.color.gray))
        binding.edtAddressStaff.isEnabled = false

        binding.cartSpinnerRole.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.spinnerRole.setBackground(resources.getDrawable(R.color.gray))

        val mlist = mutableListOf("")
        mlist.clear()
        setAdapter(mlist)
    }

    override fun UpdateError() {
        dialog.showStartDialog3(getString(R.string.UpdateError),this)
    }

    override fun AccountExist() {
        dialog.showStartDialog3(getString(R.string.AccountExist),this)
    }

    override fun UserIllegal() {
        dialog.showStartDialog3(getString(R.string.UserIllegal),this)
    }

    override fun PassIllegal() {
        dialog.showStartDialog3(getString(R.string.PassIllegal),this)
    }

    override fun CheckSuccess() {
        if (typeStaff.equals("update")) {
            customStaffPresenter.updateStaff(reqStaff)
        }
        else customStaffPresenter.insertAccount(reqAuth,reqStaff)
    }

    override fun AddAuthError() {
        dialog.showStartDialog3(getString(R.string.AddAuthError),this)
    }

    override fun StaffIdExist() {
        dialog.showStartDialog3(getString(R.string.staffId_exist),this)
    }

}