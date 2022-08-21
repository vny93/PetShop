package vn.vunganyen.petshop.screens.admin.inputData.mngBrand.updateBrand

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.admin.brand.uploadInfor.PostBrandReq
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityBrandDetailMngBinding
import java.io.IOException

class BrandDetailMngActivity : AppCompatActivity(),BrandDetMngInterface {
    lateinit var binding : ActivityBrandDetailMngBinding
    lateinit var brandDetMngPresenter: BrandDetMngPresenter
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var MY_REQUEST_CODE: Int = 10
    var bitmapUpload: Bitmap? = null
    companion object {
        lateinit var brand : BrandDetailRes
        lateinit var context: Context
        var mUri: Uri? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrandDetailMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = applicationContext
        brandDetMngPresenter = BrandDetMngPresenter(this)
        setData()
        setEvent()
        setToolbar()
    }

    fun setData(){
        brand = getIntent().getSerializableExtra("data") as BrandDetailRes
        if(!brand.logo.isEmpty()){
            val strUrl: List<String> = brand.logo.split("3000/")
            val url = PathApi.BASE_URL + strUrl.get(1)
            Picasso.get().load(url).into(binding.imvLogoBrand)
        }
        binding.edtBrandId.setText(brand.mahang)
        binding.edtBrandName.setText(brand.tenhang)
        binding.edtBrandEmail.setText(brand.email)
        binding.edtBrandPhone.setText(brand.sdt)
        if(!brand.mota.isEmpty()){
            binding.edtBrandDecs.setText(brand.mota)
        }
        binding.imvLogoBrand.isEnabled = false
    }

    fun setEvent(){
        binding.btnUpdateBrand.setOnClickListener {
            if(binding.btnUpdateBrand.text.toString().equals("Cập nhật")){
                binding.btnUpdateBrand.setText(getString(R.string.pf_btnSave))

                binding.imvLogoBrand.isEnabled = true

                binding.cartBrandName.setCardBackgroundColor(Color.WHITE)
                binding.edtBrandName.setBackground(resources.getDrawable(R.color.white))
                binding.edtBrandName.isEnabled = true

                binding.cartBrandEmail.setCardBackgroundColor(Color.WHITE)
                binding.edtBrandEmail.setBackground(resources.getDrawable(R.color.white))
                binding.edtBrandEmail.isEnabled = true

                binding.cartBrandPhone.setCardBackgroundColor(Color.WHITE)
                binding.edtBrandPhone.setBackground(resources.getDrawable(R.color.white))
                binding.edtBrandPhone.isEnabled = true

                binding.cartBrandDecs.setCardBackgroundColor(Color.WHITE)
                binding.edtBrandDecs.setBackground(resources.getDrawable(R.color.white))
                binding.edtBrandDecs.isEnabled = true
            }
            else{
                var id = binding.edtBrandId.text.toString()
                var name = binding.edtBrandName.text.toString()
                var email = binding.edtBrandEmail.text.toString()
                var phone = binding.edtBrandPhone.text.toString()
                var describe = binding.edtBrandDecs.text.toString()
                println(name)
                println(email)
                println(phone)
                println(describe)
                var req = PostBrandReq(id,name,email,phone,describe)
                brandDetMngPresenter.validCheck(req)
            }

        }
        binding.imvLogoBrand.setOnClickListener {
            onClickRequestPermission()
        }

        activityResultLauncher =
            registerForActivityResult<Intent, ActivityResult>(
                ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { result ->
                    if (result.resultCode == RESULT_OK) {
                        val data: Intent? = result.data
                        val uri = data?.data
                        if (uri != null) {
                            mUri = uri
                        }
                        try {
                            bitmapUpload =
                                MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)
                            println("Bitmap: " + bitmapUpload)
                            binding.imvLogoBrand.setImageBitmap(bitmapUpload)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            )


        binding.viewBrand.setOnClickListener{
            binding.edtBrandName.clearFocus()
            binding.edtBrandEmail.clearFocus()
            binding.edtBrandPhone.clearFocus()
            binding.edtBrandDecs.clearFocus()
            binding.viewBrand.hideKeyboard()
        }
    }

    fun onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery()
            return
        }
        if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(
                permission, MY_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String?>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"))
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
        var toolbar = binding.toolbarBrandDetail
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

    override fun UpdateError() {
        dialog.showStartDialog3(getString(R.string.UpdateBrandError),this)
    }

    override fun UpdateSuccess() {
        dialog.showStartDialog3(getString(R.string.UpdateBrandSuccess),this)
        binding.btnUpdateBrand.setText(getString(R.string.tv_save))

        binding.imvLogoBrand.isEnabled = false

        binding.cartBrandName.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtBrandName.setBackground(resources.getDrawable(R.color.gray))
        binding.edtBrandName.isEnabled = false

        binding.cartBrandEmail.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtBrandEmail.setBackground(resources.getDrawable(R.color.gray))
        binding.edtBrandEmail.isEnabled = false

        binding.cartBrandPhone.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtBrandPhone.setBackground(resources.getDrawable(R.color.gray))
        binding.edtBrandPhone.isEnabled = false

        binding.cartBrandDecs.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtBrandDecs.setBackground(resources.getDrawable(R.color.gray))
        binding.edtBrandDecs.isEnabled = false

    }
}