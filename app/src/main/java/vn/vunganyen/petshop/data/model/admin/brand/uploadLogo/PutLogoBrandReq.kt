package vn.vunganyen.petshop.data.model.admin.brand.uploadLogo

import okhttp3.MultipartBody
import retrofit2.http.Part
import java.io.Serializable

data class PutLogoBrandReq(
    val mahang : String,
) : Serializable