package vn.vunganyen.petshop.screens.admin.inputData.mngProductType.custom

import android.Manifest
import android.annotation.SuppressLint
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
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.data.model.client.productType.ProductTypeRes
import vn.vunganyen.petshop.databinding.ActivityCustomPtmnagBinding
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.io.IOException

class CustomPTMngActivity : AppCompatActivity(), CustomPTMngInterface {
    lateinit var binding : ActivityCustomPtmnagBinding
    lateinit var customPTMngPresenter: CustomPTMngPresenter
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var MY_REQUEST_CODE: Int = 10
    var bitmapUpload: Bitmap? = null
    companion object {
        lateinit var type : String
        lateinit var productType : ProductTypeRes
        lateinit var contextPTmng: Context
        var mUri: Uri? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomPtmnagBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customPTMngPresenter = CustomPTMngPresenter(this)
        contextPTmng  = applicationContext
        setData()
        setEvent()
        setToolbar()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setData(){
        type = getIntent().getStringExtra("type") as String
        if(type.equals("update")){
            productType = getIntent().getSerializableExtra("data") as ProductTypeRes
            if(productType.hinhanh != null){
                val strUrl: List<String> = productType.hinhanh.split("3000/")
                val url = PathApi.BASE_URL + strUrl.get(1)
                Picasso.get().load(url).into(binding.imvLogoPTmng)
            }
            binding.edtIdPTmng.setText(productType.maloaisp)
            binding.edtNamePTmng.setText(productType.tenloaisp)

            binding.btnUpdatePTmng.setBackground(resources.getDrawable(R.drawable.custom_button_false))
            binding.btnUpdatePTmng.isEnabled = false
        }
        else{
            binding.toolbarPtMng.setTitle(getString(R.string.tv_toolbar_insertProduct))
            binding.edtIdPTmng.isEnabled = true
            binding.cartPTMng.setBackgroundColor(Color.WHITE)
            binding.edtIdPTmng.setBackground(resources.getDrawable(R.color.white))
            binding.btnUpdatePTmng.setText(getString(R.string.tv_insert))
        }
    }

    fun setEvent(){
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
                            binding.imvLogoPTmng.setImageBitmap(bitmapUpload)
                            binding.btnUpdatePTmng.setBackground(resources.getDrawable(R.drawable.custom_button))
                            binding.btnUpdatePTmng.isEnabled = true
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            )

        binding.imvLogoPTmng.setOnClickListener {
            onClickRequestPermission()
        }

        if(type.equals("update")){
            binding.edtNamePTmng.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    val PTname = binding.edtNamePTmng.text.toString()
                    if (PTname.equals(productType.tenloaisp)) {
                        binding.btnUpdatePTmng.setBackground(resources.getDrawable(R.drawable.custom_button_false))
                        binding.btnUpdatePTmng.isEnabled = false
                    } else {
                        binding.btnUpdatePTmng.setBackground(resources.getDrawable(R.drawable.custom_button))
                        binding.btnUpdatePTmng.isEnabled = true
                    }
                }
            })
        }

        binding.btnUpdatePTmng.setOnClickListener {
            var id = binding.edtIdPTmng.text.toString()
            var name = binding.edtNamePTmng.text.toString()
            println(id)
            println(name)
            customPTMngPresenter.validCheck(id,name)
        }

        binding.viewPTmng.setOnClickListener{
            binding.edtIdPTmng.clearFocus()
            binding.edtNamePTmng.clearFocus()
            binding.viewPTmng.hideKeyboard()
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
        var toolbar = binding.toolbarPtMng
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

    override fun UpdateError() {
        dialog.showStartDialog3(getString(R.string.UpdateBrandError),this)
    }

    override fun UpdateSuccess() {
        dialog.showStartDialog3(getString(R.string.UpdateBrandSuccess),this)
        binding.btnUpdatePTmng.setBackground(resources.getDrawable(R.drawable.custom_button_false))
        binding.btnUpdatePTmng.isEnabled = false
    }

    override fun InsertError() {
        dialog.showStartDialog3(getString(R.string.tv_insert_fail),this)
    }

    override fun InsertSuccess() {
        dialog.showStartDialog3(getString(R.string.tv_insert_success),this)
        binding.edtIdPTmng.setText("")
        binding.edtNamePTmng.setText("")
        mUri = null
        bitmapUpload = null
        binding.imvLogoPTmng.setBackgroundResource(R.drawable.no_image2)
        binding.imvLogoPTmng.setImageBitmap(bitmapUpload)
    }

    override fun ImageEmpty() {
        dialog.showStartDialog3(getString(R.string.Image_empty),this)
    }

}