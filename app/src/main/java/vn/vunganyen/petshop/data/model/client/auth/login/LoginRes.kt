package vn.vunganyen.petshop.data.model.client.auth.login

data class LoginRes(
   val accessToken : String,
   val maquyen : Int,
   val trangthai : Int
)