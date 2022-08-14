package vn.vunganyen.petshop.screens.client.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import vn.vunganyen.petshop.data.adapter.AdapterProduct
import vn.vunganyen.petshop.data.model.client.brandDetail.BrandDetailRes
import vn.vunganyen.petshop.data.model.client.product.get.ProductRes
import vn.vunganyen.petshop.data.model.client.product.search.SearchProductReq
import vn.vunganyen.petshop.databinding.ActivitySearchProBinding

class SearchProActivity : AppCompatActivity(), SearchProInterface {
    lateinit var binding : ActivitySearchProBinding
    lateinit var searchProPresenter: SearchProPresenter
    var adapter : AdapterProduct = AdapterProduct()
    var listName = ArrayList<String>()
    var listBrand = ArrayList<BrandDetailRes>()
    var nameProduct = ""
    var option : String = ""
    var min = 0
    var max = 100000000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchProBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchProPresenter = SearchProPresenter(this)
        getData()
        setEvent()
    }

    fun getData(){
        binding.edtSearchProduct.requestFocus()
        searchProPresenter.getBrandName()
    }

    fun setEvent(){
        binding.spinnerBrand.setOnItemClickListener(({ adapterView, view, i, l ->
            var name = adapterView.getItemAtPosition(i).toString()
            var check =0
            for(i in 0..listBrand.size-1){
                if(name.equals(listBrand.get(i).tenhang)){
                    check = 1
                    option = listBrand.get(i).mahang
                    println("option:"+option)
                    clearFocus()
                    var req = SearchProductReq(nameProduct,option,min,max)
                    searchProPresenter.searchProduct(req)
                }
            }
            if(check == 0){
                println("qua else nè")
                option = ""
                println("option:"+option)
                clearFocus()
                var req = SearchProductReq(nameProduct,option,min,max)
                searchProPresenter.searchProduct(req)
            }
        }))

        binding.backSearch.setOnClickListener{
            finish()
        }

        binding.edtSearchProduct.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                var name = binding.edtSearchProduct.text.toString()
                nameProduct = searchProPresenter.handleString(name)
                println("name product: "+nameProduct)
                var req = SearchProductReq(nameProduct,option,min,max)
                searchProPresenter.searchProduct(req)
            }

        })

        binding.edtMin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                min = binding.edtMin.text.toString().toInt()
                var req = SearchProductReq(nameProduct,option,min,max)
                searchProPresenter.searchProduct(req)
            }
        })

        binding.edtMax.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                max = binding.edtMax.text.toString().toInt()
                var req = SearchProductReq(nameProduct,option,min,max)
                searchProPresenter.searchProduct(req)
            }

        })

        binding.lnlSearchPro.setOnClickListener{
            clearFocus()
        }
    }

    fun clearFocus(){
        binding.edtSearchProduct.clearFocus()
        binding.edtMin.clearFocus()
        binding.edtMax.clearFocus()
        binding.lnlSearchPro.hideKeyboard()
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

    override fun getBrandName(list: List<BrandDetailRes>) {
        listBrand = list as ArrayList<BrandDetailRes>
        listName.add("Tất cả")
        for(i in 0..list.size-1){
            listName.add(list.get(i).tenhang)
        }
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,listName)
        binding.spinnerBrand.setAdapter(adapter)
        binding.spinnerBrand.setHint("Hãng sản xuất")
    }

    override fun getPRoduct(list: List<ProductRes>) {
        adapter.setData(list)
        binding.rcvSearchPro.adapter = adapter
    }
}