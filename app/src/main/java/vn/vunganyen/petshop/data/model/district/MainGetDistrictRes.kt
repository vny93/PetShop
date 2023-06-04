package vn.vunganyen.petshop.data.model.district

data class MainGetDistrictRes(
    var name : String,
    var code : Long,
    var division_type : String,
    var codename : String,
    var phone_code : Long,
    var districts : List<DistrictRes>
)