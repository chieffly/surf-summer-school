package ru.chieffly.memoscope.net

import retrofit2.Call
import retrofit2.http.GET
import ru.chieffly.memoscope.model.MemDto

interface MemApi {
    @GET("/memes")
    fun getMemes () : Call<List<MemDto>>
}