package ru.chieffly.memoscope.user

import android.content.Context
import ru.chieffly.memoscope.model.UserInfo
import ru.chieffly.memoscope.utils.PreferenceHelper.get
import ru.chieffly.memoscope.utils.PreferenceHelper.set
import ru.chieffly.memoscope.model.AuthInfoDto
import ru.chieffly.memoscope.utils.PreferenceHelper

const val APP_PREFERENCES_ID = "id"
const val APP_PREFERENCES_USERNAME = "username"
const val APP_PREFERENCES_FIRSTNAME = "firstName"
const val APP_PREFERENCES_LASTNAME = "lastName"
const val APP_PREFERENCES_USERDESCR = "userDescription"
const val APP_PREFERENCES_TOKEN = "token"

class UserStorage (cntxt: Context) {
    val prefs = PreferenceHelper.defaultPrefs(cntxt)

    fun saveUserInfosaveUserInfo (user : UserInfo) {
        prefs[APP_PREFERENCES_ID] = user.id
        prefs[APP_PREFERENCES_USERNAME] = user.username
        prefs[APP_PREFERENCES_FIRSTNAME] = user.firstName
        prefs[APP_PREFERENCES_LASTNAME] = user.lastName
        prefs[APP_PREFERENCES_USERDESCR] = user.userDescription
    }

    fun saveToken (token: String) {
        prefs[APP_PREFERENCES_TOKEN] = token
    }

    fun saveAuthorization (auth : AuthInfoDto) {
        saveToken (auth.accessToken)
        saveUserInfosaveUserInfo (auth.userInfo)
    }

    fun getToken() : String {
        val token: String? =  prefs[APP_PREFERENCES_TOKEN]
        return token.toString()
    }

    fun getField (key : String): String {
        val value: String? =  prefs[key]
        return value.toString()
    }
}

