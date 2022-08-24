package vn.vunganyen.petshop.screens.admin.profile.information

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.admin.staff.getProfile.StaffRes
import vn.vunganyen.petshop.data.model.admin.staff.updateProfile.PutStaffReq
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityInforBinding
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.util.*

class InforActivity : AppCompatActivity(), InforInterface {
    lateinit var binding : ActivityInforBinding
    lateinit var inforPresenter: InforPresenter
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var profile : StaffRes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInforBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inforPresenter = InforPresenter(this)
        setData()
        setEvent()
        setToolbar()
        println("roleId: "+SplashScreenActivity.roleId)
    }

    fun setData(){
        binding.imvCalendar.isEnabled = false
        profile = SplashScreenActivity.profileAdmin.result
        binding.edtStaffId.setText(profile.manv)
        binding.edtNameStaff.setText(profile.hoten)
        binding.edtPhoneStaff.setText(profile.sdt)
        binding.edtEmailStaff.setText(profile.email)
        binding.edtAddressStaff.setText(profile.diachi)
        var date : Date = SplashScreenActivity.formatdate.parse(profile.ngaysinh)
        c.time = date
        c.add(Calendar.DATE, 1) // number of days to add
        var strDate = SplashScreenActivity.formatdate1.format(c.time)
        binding.edtBirthStaff.setText(strDate)
        if(profile.gioitinh.equals("Nam")){
            binding.radioMaleStaff.isChecked = true
        }
        else binding.radioFemaleStaff.isChecked = true
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

        binding.btnSave.setOnClickListener{
            if(binding.btnSave.text.toString().equals("Cập nhật")){
                binding.btnSave.setText(getString(R.string.pf_btnSave))
                binding.imvCalendar.isEnabled = true

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
            }
            else{
                var id = binding.edtStaffId.text.toString()
                var name = inforPresenter.handleString(binding.edtNameStaff.text.toString())
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
                var reqStaff = PutStaffReq(id,name,gender,address,strDate,phone,email)
                inforPresenter.validCheck(reqStaff)
            }
        }

        binding.lnlCustomStaff.setOnClickListener{
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

    override fun UpdateSucces() {
        dialog.showStartDialog3(getString(R.string.AddProfileSucces),this)
        SplashScreenActivity.editor.commit()
        inforPresenter.getProfileAdminEditor()
        setData()

        binding.btnSave.setText(getString(R.string.tv_save))
        binding.imvCalendar.isEnabled = false
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
    }

    override fun UpdateError() {
        dialog.showStartDialog3(getString(R.string.UpdateError),this)
    }
}