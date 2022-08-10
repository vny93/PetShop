package vn.vunganyen.petshop.data.model.product.search

data class SearchProductReq(
    val tensp: String,
    val mahang: String,
    val min: Int,
    val max: Int
)