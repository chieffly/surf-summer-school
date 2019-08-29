package ru.chieffly.memoscope.presenters

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.model.AuthInfoDto
import ru.chieffly.memoscope.net.NetworkService
import ru.chieffly.memoscope.view.dialogs.LogoutDialogFragment

class LogoutDialogPresenter (val view: LogoutDialogFragment) {
    private val ns = NetworkService.instance
    private val app: MyApp = MyApp.applicationContext() as MyApp


    fun onClearPreferences() {
        app.getStorage().clear()
    }

    fun logoutRequest() {
        val token = app.getStorage().getToken()
        ns.getAuthApi().logoutRequest(token).enqueue(object : Callback<AuthInfoDto> {
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