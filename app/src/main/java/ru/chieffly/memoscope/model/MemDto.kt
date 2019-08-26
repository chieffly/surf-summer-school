package ru.chieffly.memoscope.model

import com.google.gson.annotations.SerializedName

data class MemDto (
    @SerializedName("id") val id : Long,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("isFavorite") val isFavorite : Boolean,
    @SerializedName("createdDate") val createdDate : Long,
    @SerializedName("photoUtl") val photoUtl : String
)