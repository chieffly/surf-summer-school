package ru.chieffly.memoscope.view.main

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.model.AuthInfoDto
import ru.chieffly.memoscope.net.AuthApi
import ru.chieffly.memoscope.utils.UserStorage
import javax.inject.Inject

class LogoutDialogPresenter (val view: LogoutDialogFragment) {
    @Inject
    lateinit var storage: UserStorage
    @Inject
    lateinit var authApi: AuthApi
    init {
        MyApp.appComponent.inject(viewModel = this)
    }
    fun onClearPreferences() {
        storage.clear()
    }

    fun logoutRequest() {
        val token = storage.getToken()
        authApi.logoutRequest(token).enqueue(object : Callback<AuthInfoDto> {
            override fun onFailure(call: Call<AuthInfoDto>?, t: Throwable?) {
                println("fail ")
            }

            override fun onResponse(call: Call<AuthInfoDto>?, response: Response<AuthInfoDto>?) {
                if (response?.isSuccessful()!!) {
                    onClearPreferences()
                    view.exitToLoginScreen ()
                } else {
                }
            }
        })
    }

}