package com.latihanbyrg.tugaspertemuan12.model

import com.google.gson.annotations.SerializedName

data class Cat(
    @SerializedName("breeds")
    var breeds : ArrayList<Breeds> = arrayListOf(),

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("url")
    var url : String? = null,

    @SerializedName("width")
    var width  : Int?= null,

    @SerializedName("height")
    var height : Int?= null,

    var isBookmark: Boolean = false
)
