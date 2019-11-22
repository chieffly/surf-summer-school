package ru.chieffly.memoscope.view.login

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.model.AuthInfoDto
import ru.chieffly.memoscope.model.LoginUserRequestDto
import ru.chieffly.memoscope.net.AuthApi
import ru.chieffly.memoscope.utils.UserStorage
import javax.inject.Inject

const val ERR_ERROR = 0
const val ERR_WRONGDATA = 1
const val ERR_EMPTY = 2

class LoginPresenter (val view: LoginActivity) {

    @Inject
    lateinit var storage: UserStorage
    @Inject
    lateinit var authApi: AuthApi
    init {
        MyApp.appComponent.inject(viewModel = this)
    }
    fun onButtonLoginClicked() {
        view.showButtonProgress ()

        val acc = LoginUserRequestDto(
            view.getPhoneField(),
            view.getPassField()
        )
        authApi.loginRequest(acc).enqueue(object : Callback<AuthInfoDto> {
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
            storage.saveAuthorization(it)
        }
    }

}