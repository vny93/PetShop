package vn.vunganyen.petshop.screens.client.register.newProfile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.data.model.client.auth.login.LoginReq
import vn.vunganyen.petshop.data.model.client.user.addProfile.AddProfileReq
import vn.vunganyen.petshop.data.model.client.user.getProfile.UserReq
import vn.vunganyen.petshop.databinding.ActivityProfileBinding
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
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
        if(SplashScreenActivity.token.equals("")){
            username = getIntent().getStringExtra("username") as String
            password = getIntent().getStringExtra("password") as String
        }
        else{
            username = SplashScreenActivity.profileClient.result.tendangnhap
        }

        setData()
        setEvent()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setData(){
        binding.pfBirth.setText("" + day + "/" + (month + 1) + "/" + year)
        if(!SplashScreenActivity.token.equals("")){
            binding.pfName.setText(SplashScreenActivity.profileClient.result.hoten)
            var date : Date = SplashScreenActivity.formatdate.parse(SplashScreenActivity.profileClient.result.ngaysinh)
            c.time = date
            c.add(Calendar.DATE, 1) // number of days to add
            var strDate = SplashScreenActivity.formatdate1.format(c.time)
            binding.pfBirth.setText(strDate)
            if(SplashScreenActivity.profileClient.result.gioitinh.equals("Nam")){
                binding.radioMale.isChecked = true
            }
            else binding.radioFemale.isChecked = true
            binding.pfPhone.setText(SplashScreenActivity.profileClient.result.sdt)
            binding.rgtEmail.setText(SplashScreenActivity.profileClient.result.email)
            binding.pfAddress.setText(SplashScreenActivity.profileClient.result.diachi)
            binding.pfTaxCode.setText(SplashScreenActivity.profileClient.result.masothue)
            binding.imvCalendar.isEnabled = false
        }
        else{
            binding.btnSave.setText(getString(R.string.pf_btnSave))
            binding.cartPfName.setCardBackgroundColor(Color.WHITE)
            binding.pfName.setBackground(resources.getDrawable(R.color.white))
            binding.pfName.isEnabled = true

            binding.cartPfBirth.setCardBackgroundColor(Color.WHITE)
            binding.pfBirth.setBackground(resources.getDrawable(R.color.white))

            binding.radioFemale.isEnabled = true
            binding.radioMale.isEnabled = true

            binding.cartPfPhone.setCardBackgroundColor(Color.WHITE)
            binding.pfPhone.setBackground(resources.getDrawable(R.color.white))
            binding.pfPhone.isEnabled = true

            binding.cartRgtEmail.setCardBackgroundColor(Color.WHITE)
            binding.rgtEmail.setBackground(resources.getDrawable(R.color.white))
            binding.rgtEmail.isEnabled = true

            binding.cartPfAddress.setCardBackgroundColor(Color.WHITE)
            binding.pfAddress.setBackground(resources.getDrawable(R.color.white))
            binding.pfAddress.isEnabled = true

            binding.cartPfTaxCode.setCardBackgroundColor(Color.WHITE)
            binding.pfTaxCode.setBackground(resources.getDrawable(R.color.white))
            binding.pfTaxCode.isEnabled = true
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
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
            if(binding.btnSave.text.toString().equals("Cập nhật")){
                binding.btnSave.setText(getString(R.string.pf_btnSave))
                binding.imvCalendar.isEnabled = true
                binding.cartPfName.setCardBackgroundColor(Color.WHITE)
                binding.pfName.setBackground(resources.getDrawable(R.color.white))
                binding.pfName.isEnabled = true

                binding.cartPfBirth.setCardBackgroundColor(Color.WHITE)
                binding.pfBirth.setBackground(resources.getDrawable(R.color.white))

                binding.radioFemale.isEnabled = true
                binding.radioMale.isEnabled = true

                binding.cartPfPhone.setCardBackgroundColor(Color.WHITE)
                binding.pfPhone.setBackground(resources.getDrawable(R.color.white))
                binding.pfPhone.isEnabled = true

                binding.cartRgtEmail.setCardBackgroundColor(Color.WHITE)
                binding.rgtEmail.setBackground(resources.getDrawable(R.color.white))
                binding.rgtEmail.isEnabled = true

                binding.cartPfAddress.setCardBackgroundColor(Color.WHITE)
                binding.pfAddress.setBackground(resources.getDrawable(R.color.white))
                binding.pfAddress.isEnabled = true

                binding.cartPfTaxCode.setCardBackgroundColor(Color.WHITE)
                binding.pfTaxCode.setBackground(resources.getDrawable(R.color.white))
                binding.pfTaxCode.isEnabled = true
            }
            else{
                var name = profilePresenter.handleString(binding.pfName.text.toString())
                var dateBirth = binding.pfBirth.text.toString()
                var mdate : Date = SplashScreenActivity.formatdate1.parse(dateBirth)
                var strDate = SplashScreenActivity.formatdate.format(mdate)
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
        SplashScreenActivity.editor.commit()
        profilePresenter.getProfileEditor()
        binding.btnSave.setText(getString(R.string.tv_save))
        binding.btnSave.setText(getString(R.string.pf_btnSave))
        binding.imvCalendar.isEnabled = false
        binding.cartPfName.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.pfName.setBackground(resources.getDrawable(R.color.gray))
        binding.pfName.isEnabled = false

        binding.cartPfBirth.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.pfBirth.setBackground(resources.getDrawable(R.color.gray))

        binding.radioFemale.isEnabled = false
        binding.radioMale.isEnabled = false

        binding.cartPfPhone.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.pfPhone.setBackground(resources.getDrawable(R.color.gray))
        binding.pfPhone.isEnabled = false

        binding.cartRgtEmail.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.rgtEmail.setBackground(resources.getDrawable(R.color.gray))
        binding.rgtEmail.isEnabled = false

        binding.cartPfAddress.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.pfAddress.setBackground(resources.getDrawable(R.color.gray))
        binding.pfAddress.isEnabled = false

        binding.cartPfTaxCode.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.pfTaxCode.setBackground(resources.getDrawable(R.color.gray))
        binding.pfTaxCode.isEnabled = false

    }


    override fun UpdateError() {
        dialog.showStartDialog3(getString(R.string.UpdateError),this)
    }

    override fun OrlError() {
        dialog.showStartDialog3(getString(R.string.staffId_old),this)
    }
}