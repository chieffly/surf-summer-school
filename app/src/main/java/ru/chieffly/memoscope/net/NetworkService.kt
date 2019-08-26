package ru.chieffly.memoscope.net

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


const private val BASE_URL = "https://demo3161256.mockable.io/"

class NetworkService private constructor() {
    private val retrofit: Retrofit
    init {

        retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        val instance: NetworkService by lazy(LazyThreadSafetyMode.PUBLICATION) { NetworkService() }
    }

    fun getAuthApi () : AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    fun getMemApi () : MemApi {
        return retrofit.create(MemApi::class.java)
    }
}