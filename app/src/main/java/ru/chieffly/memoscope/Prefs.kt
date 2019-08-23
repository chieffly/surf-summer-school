package ru.chieffly.memoscope

import android.content.Context

class Prefs(cntxt: Context) {

    var MY_PREFS_NAME = "settings"
    private var context: Context = cntxt
    private var settings = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
    private var editor = settings.edit()

    companion object {
        const val APP_PREFERENCES_ID = "id"
        const val APP_PREFERENCES_USERNAME = "username"
        const val APP_PREFERENCES_FIRSTNAME = "firstName"
        const val APP_PREFERENCES_LASTNAME = "lastName"
        const val APP_PREFERENCES_USERDESCR = "userDescription"
    }

    fun addString(name: String, value: String) {
        editor.putString(name, value)
        editor.apply()
    }

    fun getString(key: String ): String
    {
        return settings.getString(key, null);
    }

    fun getInt(key: String ): Int
    {
        return settings.getInt(key, 0);
    }

    fun saveUser (user : UserInfo) {
        editor.putInt(APP_PREFERENCES_ID, user.id)
        editor.putString(APP_PREFERENCES_USERNAME, user.username)
        editor.putString(APP_PREFERENCES_FIRSTNAME, user.firstName)
        editor.putString(APP_PREFERENCES_LASTNAME, user.lastName)
        editor.putString(APP_PREFERENCES_USERDESCR, user.userDescription)
        editor.apply()
    }
}
