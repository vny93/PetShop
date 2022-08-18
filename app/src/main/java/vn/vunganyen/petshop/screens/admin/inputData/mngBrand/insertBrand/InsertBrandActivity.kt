package vn.vunganyen.petshop.screens.admin.inputData.mngBrand.insertBrand

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.databinding.ActivityInsertBrandBinding
import vn.vunganyen.petshop.screens.admin.inputData.mngBrand.updateBrand.BrandDetailMngActivity
import java.io.IOException

class InsertBrandActivity : AppCompatActivity(),InsertBrandInterface {
    lateinit var binding : ActivityInsertBrandBinding
    lateinit var insertBrandPresenter: InsertBrandPresenter
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var MY_REQUEST_CODE: Int = 10
    var bitmapUpload: Bitmap? = null
    companion object {
        lateinit var contextBrand: Context
        var mUri: Uri? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBrandBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contextBrand = applicationContext
        insertBrandPresenter = InsertBrandPresenter(this)
        setEvent()
        setToolbar()
    }

    fun setEvent(){
        binding.btnInsertBrand.setOnClickListener {
            var id = binding.edtBrandId.text.toString()
            var name = binding.edtBrandName.text.toString()
            var email = binding.edtBrandEmail.text.toString()
            var phone = binding.edtBrandPhone.text.toString()
            var describe = binding.edtBrandDecs.text.toString()
            var logo = " "
            println(name)
            println(email)
            println(phone)
            println(describe)
            var req = BrandDetailRes(id,name,email,phone,logo,describe)
            insertBrandPresenter.validCheck(req)
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
                            BrandDetailMngActivity.mUri = uri
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

    override fun ImageEmpty() {
        dialog.showStartDialog3(getString(R.string.Image_empty),this)
    }

    override fun BrandIdExist() {
        dialog.showStartDialog3(getString(R.string.BrandId_exist),this)
    }

    override fun EmailExist() {
        dialog.showStartDialog3(getString(R.string.EmailExist),this)
    }

    override fun InsertError() {
        dialog.showStartDialog3(getString(R.string.tv_insert_fail),this)
    }

    override fun InsertSuccess() {
        dialog.showStartDialog3(getString(R.string.tv_insert_success),this)
        binding.edtBrandId.setText("")
        binding.edtBrandName.setText("")
        binding.edtBrandEmail.setText("")
        binding.edtBrandPhone.setText("")
        binding.edtBrandDecs.setText("")
        mUri = null
        bitmapUpload = null
        binding.imvLogoBrand.setBackgroundResource(R.drawable.no_image2)
        binding.imvLogoBrand.setImageBitmap(bitmapUpload)
    }
}