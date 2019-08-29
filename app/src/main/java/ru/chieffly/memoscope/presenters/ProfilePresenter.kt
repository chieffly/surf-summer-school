package ru.chieffly.memoscope.presenters

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.utils.APP_PREFERENCES_USERDESCR
import ru.chieffly.memoscope.utils.APP_PREFERENCES_USERNAME
import ru.chieffly.memoscope.view.fragment.FragmentProfile

class ProfilePresenter (val view: FragmentProfile) {

    private val app: MyApp = MyApp.applicationContext() as MyApp


    fun getUserName():String {
        return app.getStorage().getField(APP_PREFERENCES_USERNAME)
    }

    fun getUserDescr():String {
        return app.getStorage().getField(APP_PREFERENCES_USERDESCR)
    }

    fun onInit () {
        val userId = app.getStorage().getUserId()
        showMyMemsFromDB(userId)
    }

    fun showMyMemsFromDB (userId: Int)  {
        app.getDB().getByCreatorId(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe{ Mem -> view.showDashboard(Mem)}
    }

}