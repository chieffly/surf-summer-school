package ru.chieffly.memoscope

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface NetworkService {

    @POST("/auth/login")
    fun registrationPost (@Body body : LoginUserRequestDto ) : Call<AuthInfoDto>

    companion object {

        fun create(): NetworkService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://demo3161256.mockable.io/")
                .build()

            return retrofit.create(NetworkService::class.java)
        }
    }

}

data class LoginUserRequestDto (
    @SerializedName("login") val login : String,
    @SerializedName("password") val password : String
)

data class UserInfo (
    @SerializedName("id") val id : Int,
    @SerializedName("username") val username : String,
    @SerializedName("firstName") val firstName : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("userDescription") val userDescription : String
)

data class AuthInfoDto (
    @SerializedName("accessToken") val accessToken : String,
    @SerializedName("userInfo") val userInfo : UserInfo
)
data class MemDto (
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("isFavorite") val isFavorite : String,
    @SerializedName("createdDate") val createdDate : String,
    @SerializedName("photoUtl") val photoUtl : String
)

data class ErrorResponseDto (
    @SerializedName("code") val code : String,
    @SerializedName("errorMessage") val errorMessage : String
)
