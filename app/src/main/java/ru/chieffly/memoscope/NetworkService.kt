package ru.chieffly.memoscope

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface NetworkService {

    @POST("/auth/login")
    fun registrationPost (@Body body : AuthRequestModel ) : Call<AuthResponseModel>

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