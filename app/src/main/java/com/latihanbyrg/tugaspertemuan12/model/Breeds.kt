package com.latihanbyrg.tugaspertemuan12.model

import com.google.gson.annotations.SerializedName

data class Breeds(
    @SerializedName("weight")
    val weight: Weight,

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("temperament")
    val temperament: String,

    @SerializedName("origin")
    val origin: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("life_span")
    val lifeSpan: String,

    )

