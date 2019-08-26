package ru.chieffly.memoscope.net

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.chieffly.memoscope.model.AuthInfoDto
import ru.chieffly.memoscope.model.LoginUserRequestDto
import ru.chieffly.memoscope.model.MemDto

interface AuthApi {
    @POST("/auth/login")
    fun registrationPost (@Body body : LoginUserRequestDto) : Call<AuthInfoDto>

    @POST("/auth/loout")
    fun logoutPost (@Body body : LoginUserRequestDto) : Call<AuthInfoDto>
}