package ru.chieffly.memoscope.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class MemDto (
    @PrimaryKey @SerializedName("id") val id : Long,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("isFavorite") var isFavorite : Boolean,
    @SerializedName("createdDate") val createdDate : Long,
    @SerializedName("photoUtl") val photoUtl : String
) : Serializable