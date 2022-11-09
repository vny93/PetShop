package vn.vunganyen.petshop.data.api

import java.io.Serializable

class PathApi : Serializable {
    companion object{
        const val BASE_URL ="http://192.168.2.14:3000/"
        const val BASE_URL2 ="http://192.168.2.14:4000/"
        const val BASE_URL3 ="https://graphhopper.com/api/1/route/"
    }
}