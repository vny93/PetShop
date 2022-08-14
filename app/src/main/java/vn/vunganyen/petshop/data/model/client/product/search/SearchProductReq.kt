package vn.vunganyen.petshop.data.model.client.product.search

data class SearchProductReq(
    val tensp: String,
    val mahang: String,
    val min: Int,
    val max: Int
)