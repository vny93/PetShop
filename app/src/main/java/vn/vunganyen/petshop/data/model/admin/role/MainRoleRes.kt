package vn.vunganyen.petshop.data.model.admin.role

import vn.vunganyen.petshop.data.model.client.register.findRole.FindRoleRes

data class MainRoleRes(
    var result : List<FindRoleRes>
)