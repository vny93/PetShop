package vn.vunganyen.petshop.screens.client.register.newProfile

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.classSupport.StartAlertDialog
import vn.vunganyen.petshop.data.model.auth.login.LoginReq
import vn.vunganyen.petshop.data.model.user.addProfile.AddProfileReq
import vn.vunganyen.petshop.data.model.user.getProfile.UserReq
import vn.vunganyen.petshop.databinding.ActivityProfileBinding
import vn.vunganyen.petshop.screens.client.home.main.HomeActivity
import java.util.*

class ProfileActivity : AppCompatActivity(), ProfileInterface {
    lateinit var binding : ActivityProfileBinding
    lateinit var profilePresenter: ProfilePresenter
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var dialog: StartAlertDialog = StartAlertDialog()
    var username = ""
    var password = ""

    companion object{
        var clickOk: ((req : LoginReq)->Unit)?=null
        var clickUpdateOk: ((req : UserReq)->Unit)?=null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        profilePresenter = ProfilePresenter(this)
        if(HomeActivity.token.equals("")){
            username = getIntent().getStringExtra("username") as String
            password = getIntent().getStringExtra("password") as String
        }
        else{
            username = HomeActivity.profile.result.tendangnhap
        }

        setData()
        setEvent()
    }

    fun setData(){
        binding.pfBirth.setText("" + day + "/" + (month + 1) + "/" + year)
        if(!HomeActivity.token.equals("")){
            binding.pfName.setText(HomeActivity.profile.result.hoten)
            var date : Date = HomeActivity.formatdate.parse(HomeActivity.profile.result.ngaysinh)
            var strDate = HomeActivity.formatdate1.format(date)
            binding.pfBirth.setText(strDate)
            if(HomeActivity.profile.result.gioitinh.equals("Nam")){
                binding.radioMale.isChecked = true
            }
            else binding.radioFemale.isChecked = true
            binding.pfPhone.setText(HomeActivity.profile.result.sdt)
            binding.rgtEmail.setText(HomeActivity.profile.result.email)
            binding.pfAddress.setText(HomeActivity.profile.result.diachi)
            binding.pfTaxCode.setText(HomeActivity.profile.result.masothue)
        }
    }

    fun setEvent(){
        binding.backProfile.setOnClickListener{
            finish()
        }

        binding.imvCalendar.setOnClickListener {
            val dpd =
                this?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            binding.pfBirth.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
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
//        var gender =""
//        binding.radGLbt.setOnCheckedChangeListener { radioGroup, i ->
//            gender = clickRadio(i)
//        }

        binding.btnSave.setOnClickListener{
            var name = binding.pfName.text.toString()
            var dateBirth = binding.pfBirth.text.toString()
            var mdate : Date = HomeActivity.formatdate1.parse(dateBirth)
            var strDate = HomeActivity.formatdate.format(mdate)
            var email = binding.rgtEmail.text.toString()
            var phone = binding.pfPhone.text.toString()
            var address = binding.pfAddress.text.toString()
            var taxCode = binding.pfTaxCode.text.toString()
            var gender =""
            if(binding.radioMale.isChecked == true){
                gender = "Nam"
            }
            if(binding.radioFemale.isChecked == true){
                gender = "Nữ"
            }
            println("name: "+name)
            println("strDate: "+strDate)
            println("gendar: "+gender)
            println("email: "+email)
            println("phone: "+phone)
            println("address: "+address)
            println("taxCode: "+taxCode)
            println("username: "+username)
            var profileReq = AddProfileReq(name,gender,address,strDate,phone,email,taxCode,username)
            profilePresenter.validCheck(profileReq)
        }

        binding.lnlAddProfile.setOnClickListener{
            binding.pfName.clearFocus()
            binding.rgtEmail.clearFocus()
            binding.pfPhone.clearFocus()
            binding.pfAddress.clearFocus()
            binding.pfTaxCode.clearFocus()
            binding.lnlAddProfile.hideKeyboard()
        }
    }

//    fun clickRadio(id: Int): String {
//        if (id == R.id.radio_female) {
//            return "Nữ"
//        } else if (id == R.id.radio_male) {
//            return "Nam"
//        }
//        return ""
//    }

    fun View.hideKeyboard(): Boolean {
        try {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        } catch (ignored: RuntimeException) {
        }
        return false
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
        dialog.showStartDialog2(getString(R.string.AddProfileSucces),this)
        dialog.clickOk = {
            -> clickOk?.invoke(LoginReq(username,password))
//            var intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
        }
    }

    override fun UpdateSucces() {
        dialog.showStartDialog3(getString(R.string.AddProfileSucces),this)
        HomeActivity.editor.commit()
        profilePresenter.getProfileEditor()
    }


    override fun UpdateError() {
        dialog.showStartDialog3(getString(R.string.UpdateError),this)
    }
}