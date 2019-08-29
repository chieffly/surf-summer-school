package ru.chieffly.memoscope.net

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import ru.chieffly.memoscope.model.MemDto

interface MemApi {
    @GET("/memes")
    fun getMemes (@Header("authorization") token: String) : Call<List<MemDto>>
}