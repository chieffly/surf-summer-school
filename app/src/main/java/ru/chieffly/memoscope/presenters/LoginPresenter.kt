package ru.chieffly.memoscope.presenters

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.model.AuthInfoDto
import ru.chieffly.memoscope.model.LoginUserRequestDto
import ru.chieffly.memoscope.net.NetworkService
import ru.chieffly.memoscope.view.activity.LoginActivity

const val ERR_ERROR = 0
const val ERR_WRONGDATA = 1
const val ERR_EMPTY = 2

class LoginPresenter (val view: LoginActivity) {
    private val app: MyApp = MyApp.applicationContext() as MyApp
    private val ns = NetworkService.instance

    fun onButtonLoginClicked() {
        view.showButtonProgress ()

        val acc = LoginUserRequestDto(
            view.getPhoneField(),
            view.getPassField()
        )
        ns.getAuthApi().loginRequest(acc).enqueue(object : Callback<AuthInfoDto> {
            override fun onFailure(call: Call<AuthInfoDto>?, t: Throwable?) {
                view.showErroSnack(ERR_ERROR)
                view.hideButtonProgress()
            }

            override fun onResponse(call: Call<AuthInfoDto>?, response: Response<AuthInfoDto>?) {
                if (response?.isSuccessful!!) {
                    view.hideButtonProgress()
                    saveAuth (response)
                    view.openMainScreen()

                } else {
                    view.showErroSnack(ERR_WRONGDATA)
                    view.hideButtonProgress()

                }
            }
        })
    }

    fun saveAuth (response:  Response<AuthInfoDto> ) {
        response.body()?.let {
            app.getStorage().saveAuthorization(it)
        }
    }

}