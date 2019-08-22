package ru.chieffly.memoscope

import com.google.gson.annotations.SerializedName

data class AuthRequestModel (
    @SerializedName("login") val login : String,
    @SerializedName("password") val password : String
)
