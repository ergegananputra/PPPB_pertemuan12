package com.latihanbyrg.tugaspertemuan12.database.model


data class CatTable(
    var id: String = "",
    var catId: String = id,
    var url: String ="",
    var weight: String ="",
    var name: String ="",
    var temperament: String ="",
    var origin: String ="",
    var description: String ="",
    var lifeSpan: String ="",
    var petName: String = "Favourite Cat $id",

)
