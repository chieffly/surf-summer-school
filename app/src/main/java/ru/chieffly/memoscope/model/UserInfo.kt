package ru.chieffly.memoscope.model

import com.google.gson.annotations.SerializedName

data class UserInfo (
    @SerializedName("id") val id : Int,
    @SerializedName("username") val username : String,
    @SerializedName("firstName") val firstName : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("userDescription") val userDescription : String
)