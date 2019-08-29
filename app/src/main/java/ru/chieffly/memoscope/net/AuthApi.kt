package ru.chieffly.memoscope.net

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.chieffly.memoscope.model.AuthInfoDto
import ru.chieffly.memoscope.model.LoginUserRequestDto
import ru.chieffly.memoscope.model.MemDto

interface AuthApi {
    @POST("/auth/login")
    fun loginRequest (@Body body: LoginUserRequestDto) : Call<AuthInfoDto>

    @POST("/auth/logout")
    fun logoutRequest (@Header("authorization") token: String) : Call<AuthInfoDto>
}