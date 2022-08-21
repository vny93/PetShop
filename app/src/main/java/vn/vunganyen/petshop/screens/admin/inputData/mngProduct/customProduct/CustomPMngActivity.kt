package vn.vunganyen.petshop.screens.admin.inputData.mngProduct.customProduct

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.data.api.PathApi
import vn.vunganyen.petshop.data.model.admin.product.getList.ProductOriginalRes
import vn.vunganyen.petshop.data.model.admin.product.uploadInfor.PutProductInforReq
import vn.vunganyen.petshop.data.model.admin.provider.getlist.ProviderRes
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.client.classSupport.StartAlertDialog
import vn.vunganyen.petshop.data.model.client.product.search.SearchProductReq
import vn.vunganyen.petshop.data.model.client.productType.ProductTypeRes
import vn.vunganyen.petshop.databinding.ActivityCustomPmngBinding
import vn.vunganyen.petshop.screens.admin.inputData.mngProductType.custom.CustomPTMngActivity
import vn.vunganyen.petshop.screens.splashScreen.SplashScreenActivity
import java.io.IOException

class CustomPMngActivity : AppCompatActivity(),CustomPMngInterface {
    lateinit var binding : ActivityCustomPmngBinding
    lateinit var customPMngPresenter: CustomPMngPresenter
    var dialog: StartAlertDialog = StartAlertDialog()
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    var MY_REQUEST_CODE: Int = 10
    var bitmapUpload: Bitmap? = null
    var listBrand = ArrayList<BrandDetailRes>()
    var listNameBrand = ArrayList<String>()
    var listPT = ArrayList<ProductTypeRes>()
    var listNamePT = ArrayList<String>()
    var listProvider = ArrayList<ProviderRes>()
    var listNameProvider = ArrayList<String>()
    var idBrand = ""
    var idProvider = ""
    var idProductType = ""
    var isnew = 0
    var isgood = 0
    companion object {
        lateinit var typeProduct : String
        lateinit var product : ProductOriginalRes
        lateinit var contextPmng: Context
        var mUri: Uri? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomPmngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customPMngPresenter = CustomPMngPresenter(this)
        contextPmng = applicationContext
        setData()
        setEvent()
        setToolbar()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setData(){
        typeProduct = getIntent().getStringExtra("type") as String
        customPMngPresenter.getBrandName()
        customPMngPresenter.getListPT()
        customPMngPresenter.getListProvider()
        if(typeProduct.equals("update")){
            product = getIntent().getSerializableExtra("data") as ProductOriginalRes
            if(product.hinhanh != null){
                val strUrl: List<String> = product.hinhanh.split("3000/")
                val url = PathApi.BASE_URL + strUrl.get(1)
                Picasso.get().load(url).into(binding.imvProductMng)
            }
            binding.edtProductId.setText(product.masp)
            binding.edtProductName.setText(product.tensp)
            binding.edtProductPrice.setText(product.gia.toInt().toString())
            binding.edtProductNumber.setText(product.soluong.toString())
            binding.edtProductDecs.setText(product.mota)
            if(product.isnew == 1){
                binding.checkboxIsnew.setChecked(true);
            }
            if(product.isgood == 1){
                binding.checkboxIsgood.setChecked(true)
            }
            binding.imvProductMng.isEnabled = false
            idBrand = product.mahang
            idProductType = product.maloaisp
            idProvider = product.manhacc
        }
        else{
            binding.toolbarProductDetail.setTitle(getString(R.string.tv_toolbar_insertProduct))
            binding.edtProductId.isEnabled = true
            binding.cartProductId.setBackgroundColor(Color.WHITE)
            binding.edtProductId.setBackground(resources.getDrawable(R.color.white))
            binding.btnClickProduct.setText(getString(R.string.tv_insert))

            binding.cartProductName.setCardBackgroundColor(Color.WHITE)
            binding.edtProductName.setBackground(resources.getDrawable(R.color.white))
            binding.edtProductName.isEnabled = true

            binding.cartProductPrice.setCardBackgroundColor(Color.WHITE)
            binding.edtProductPrice.setBackground(resources.getDrawable(R.color.white))
            binding.edtProductPrice.isEnabled = true

            binding.cartProductNumber.setCardBackgroundColor(Color.WHITE)
            binding.edtProductNumber.setBackground(resources.getDrawable(R.color.white))
            binding.edtProductNumber.isEnabled = true

            binding.cartProductDecs.setCardBackgroundColor(Color.WHITE)
            binding.edtProductDecs.setBackground(resources.getDrawable(R.color.white))
            binding.edtProductDecs.isEnabled = true

            binding.cartSpinnerBrand.setCardBackgroundColor(Color.WHITE)
            binding.spinnerBrand.setBackground(resources.getDrawable(R.color.white))

            binding.cartSpinnerPt.setCardBackgroundColor(Color.WHITE)
            binding.spinnerPt.setBackground(resources.getDrawable(R.color.white))

            binding.cartSpinnerProvider.setCardBackgroundColor(Color.WHITE)
            binding.spinnerProvider.setBackground(resources.getDrawable(R.color.white))

            binding.checkboxIsnew.isEnabled = true
            binding.checkboxIsgood.isEnabled = true
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
                            binding.imvProductMng.setImageBitmap(bitmapUpload)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            )
        binding.spinnerBrand.setOnItemClickListener(({adapterView, view, i , l ->
            for(list in listBrand){
                if(list.tenhang.equals(adapterView.getItemAtPosition(i).toString())){
                    idBrand = list.mahang
                }
            }
        }))

        binding.spinnerPt.setOnItemClickListener(({adapterView, view, i , l ->
            for(list in listPT){
                if(list.tenloaisp.equals(adapterView.getItemAtPosition(i).toString())){
                    idProductType = list.maloaisp
                }
            }
        }))

        binding.spinnerProvider.setOnItemClickListener(({adapterView, view, i , l ->
            for(list in listProvider){
                if(list.tennhacc.equals(adapterView.getItemAtPosition(i).toString())){
                    idProvider = list.manhacc
                }
            }
        }))

        binding.checkboxIsgood.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked == true) isgood = 1
            else isgood = 0
        }
        binding.checkboxIsnew.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked == true) isnew = 1
            else isnew = 0
        }

        binding.btnClickProduct.setOnClickListener {
            if(binding.btnClickProduct.text.toString().equals("Cập nhật")){
                binding.btnClickProduct.setText(getString(R.string.pf_btnSave))
                setAdapterPT(listNamePT)
                setAdapterBrand(listNameBrand)
                setAdapterProvider(listNameProvider)
                binding.imvProductMng.isEnabled = true

                binding.cartProductName.setCardBackgroundColor(Color.WHITE)
                binding.edtProductName.setBackground(resources.getDrawable(R.color.white))
                binding.edtProductName.isEnabled = true

                binding.cartProductPrice.setCardBackgroundColor(Color.WHITE)
                binding.edtProductPrice.setBackground(resources.getDrawable(R.color.white))
                binding.edtProductPrice.isEnabled = true

                binding.cartProductNumber.setCardBackgroundColor(Color.WHITE)
                binding.edtProductNumber.setBackground(resources.getDrawable(R.color.white))
                binding.edtProductNumber.isEnabled = true

                binding.cartProductDecs.setCardBackgroundColor(Color.WHITE)
                binding.edtProductDecs.setBackground(resources.getDrawable(R.color.white))
                binding.edtProductDecs.isEnabled = true

                binding.cartSpinnerBrand.setCardBackgroundColor(Color.WHITE)
                binding.spinnerBrand.setBackground(resources.getDrawable(R.color.white))

                binding.cartSpinnerPt.setCardBackgroundColor(Color.WHITE)
                binding.spinnerPt.setBackground(resources.getDrawable(R.color.white))

                binding.cartSpinnerProvider.setCardBackgroundColor(Color.WHITE)
                binding.spinnerProvider.setBackground(resources.getDrawable(R.color.white))

                binding.checkboxIsnew.isEnabled = true
                binding.checkboxIsgood.isEnabled = true
            }
            else{
                var id = binding.edtProductId.text.toString()
                var name = binding.edtProductName.text.toString()
                var price = binding.edtProductPrice.text.toString()
                var number = binding.edtProductNumber.text.toString()
                var decs = binding.edtProductDecs.text.toString()
                println("id"+id)
                println("name"+name)
                println("price"+price)
                println("number"+number)
                println("decs"+decs)
                println("idBrand"+idBrand)
                println("idProvider"+idProvider)
                println("idProductType"+idProductType)
                println("isgood"+isgood)
                println("isnew"+isnew)
                var mPrice = -1.0F
                var mNumber = -1
                if(!price.isEmpty()){
                    mPrice= price.toFloat()
                }
                if(!number.isEmpty()){
                    mNumber= number.toInt()
                }
                var req = PutProductInforReq(id,name,mPrice,mNumber,decs,idProductType,idBrand,idProvider,isgood,isnew)
                customPMngPresenter.validCheck(req)
            }
        }

        binding.imvProductMng.setOnClickListener {
            onClickRequestPermission()
        }

        binding.viewProduct.setOnClickListener{
            binding.edtProductId.clearFocus()
            binding.edtProductName.clearFocus()
            binding.edtProductPrice.clearFocus()
            binding.edtProductNumber.clearFocus()
            binding.edtProductDecs.clearFocus()
            binding.viewProduct.hideKeyboard()
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
        var toolbar = binding.toolbarProductDetail
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    fun setSpinnerBrand(){
        for(list in listBrand){
            if(product.mahang.equals(list.mahang)){
                binding.spinnerBrand.setText(list.tenhang,false)
            }
        }
    }

    fun setSpinnerPT(){
        for(list in listPT){
            if(product.maloaisp.equals(list.maloaisp)){
                binding.spinnerPt.setText(list.tenloaisp,false)
            }
        }
    }

    fun setSpinnerProvider(){
        for(list in listProvider){
            if(product.manhacc.equals(list.manhacc)){
                binding.spinnerProvider.setText(list.tennhacc,false)
            }
        }
    }

    fun setAdapterBrand(list : List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerBrand.setAdapter(adapter)
        binding.spinnerBrand.setHint("Hãng sản xuất")
    }

    fun setAdapterPT(list : List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerPt.setAdapter(adapter)
        binding.spinnerPt.setHint("Loại sản phẩm")
    }

    fun setAdapterProvider(list : List<String>){
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerProvider.setAdapter(adapter)
        binding.spinnerProvider.setHint("Nhà cung cấp")
    }

    override fun GetListBrand(list: List<BrandDetailRes>) {
        listBrand = list as ArrayList<BrandDetailRes>
        for(i in 0..list.size-1){
            listNameBrand.add(list.get(i).tenhang)
        }
        if(!binding.btnClickProduct.text.toString().equals("Cập nhật")){
            setAdapterBrand(listNameBrand)
        }
        if(typeProduct.equals("update")){
            setSpinnerBrand()
        }
    }

    override fun GetListPT(list: List<ProductTypeRes>) {
        listPT = list as ArrayList<ProductTypeRes>
        for(i in 0..list.size-1){
            listNamePT.add(list.get(i).tenloaisp)
        }
        if(!binding.btnClickProduct.text.toString().equals("Cập nhật")){
            setAdapterPT(listNamePT)
        }
        if(typeProduct.equals("update")){
            setSpinnerPT()
        }
    }

    override fun GetListProvider(list: List<ProviderRes>) {
        listProvider = list as ArrayList<ProviderRes>
        for(i in 0..list.size-1){
            listNameProvider.add(list.get(i).tennhacc)
        }
        if(!binding.btnClickProduct.text.toString().equals("Cập nhật")){
            setAdapterProvider(listNameProvider)
        }
        if(typeProduct.equals("update")){
            setSpinnerProvider()
        }
    }

    override fun Empty() {
        dialog.showStartDialog3(getString(R.string.error_empty),this)
    }

    override fun PriceError() {
        dialog.showStartDialog3(getString(R.string.tv_price_error),this)
    }

    override fun NumberError() {
        dialog.showStartDialog3(getString(R.string.tv_number_error),this)
    }

    override fun UpdateError() {
        dialog.showStartDialog3(getString(R.string.UpdateBrandError),this)
    }

    override fun UpdateSuccess() {
        dialog.showStartDialog3(getString(R.string.UpdateBrandSuccess),this)
        binding.btnClickProduct.setText(getString(R.string.tv_save))
        binding.imvProductMng.isEnabled = false

        binding.cartProductName.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtProductName.setBackground(resources.getDrawable(R.color.gray))
        binding.edtProductName.isEnabled = false

        binding.cartProductPrice.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtProductPrice.setBackground(resources.getDrawable(R.color.gray))
        binding.edtProductPrice.isEnabled = false

        binding.cartProductNumber.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtProductNumber.setBackground(resources.getDrawable(R.color.gray))
        binding.edtProductNumber.isEnabled = false

        binding.cartProductDecs.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.edtProductDecs.setBackground(resources.getDrawable(R.color.gray))
        binding.edtProductDecs.isEnabled = false

        binding.cartSpinnerBrand.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.spinnerBrand.setBackground(resources.getDrawable(R.color.gray))

        binding.cartSpinnerPt.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.spinnerPt.setBackground(resources.getDrawable(R.color.gray))

        binding.cartSpinnerProvider.setCardBackgroundColor(Color.parseColor("#EFEDED"))
        binding.spinnerProvider.setBackground(resources.getDrawable(R.color.gray))

        binding.checkboxIsnew.isEnabled = false
        binding.checkboxIsgood.isEnabled = false
        val mlist = mutableListOf("")
        mlist.clear()
        setAdapterBrand(mlist)
        setAdapterPT(mlist)
        setAdapterProvider(mlist)
    }

    override fun InsertError() {
        dialog.showStartDialog3(getString(R.string.tv_insert_fail),this)
    }

    override fun InsertSuccess() {
        dialog.showStartDialog3(getString(R.string.tv_insert_success),this)
        binding.edtProductId.setText("")
        binding.edtProductName.setText("")
        binding.edtProductPrice.setText("")
        binding.edtProductNumber.setText("")
        binding.edtProductDecs.setText("")
        binding.spinnerProvider.setText("",false)
        binding.spinnerBrand.setText("",false)
        binding.spinnerPt.setText("",false)
        binding.checkboxIsgood.setChecked(false)
        binding.checkboxIsnew.setChecked(false)
        mUri = null
        bitmapUpload = null
        binding.imvProductMng.setBackgroundResource(R.drawable.no_image2)
        binding.imvProductMng.setImageBitmap(bitmapUpload)
    }

    override fun ImageEmpty() {
        dialog.showStartDialog3(getString(R.string.Image_empty),this)
    }

    override fun IdExis() {
        dialog.showStartDialog3(getString(R.string.id_product_exist),this)
    }
}