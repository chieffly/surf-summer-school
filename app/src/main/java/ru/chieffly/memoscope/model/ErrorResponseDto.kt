package ru.chieffly.memoscope.model

import com.google.gson.annotations.SerializedName

data class ErrorResponseDto (
    @SerializedName("code") val code : String,
    @SerializedName("errorMessage") val errorMessage : String
)
