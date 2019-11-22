package ru.chieffly.memoscope.view.main.fragments

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.db.MemDao
import ru.chieffly.memoscope.utils.APP_PREFERENCES_USERDESCR
import ru.chieffly.memoscope.utils.APP_PREFERENCES_USERNAME
import ru.chieffly.memoscope.utils.UserStorage
import ru.chieffly.memoscope.view.main.fragments.FragmentProfile
import javax.inject.Inject

class ProfilePresenter (val view: FragmentProfile) {

    @Inject
    lateinit var storage: UserStorage
    @Inject
    lateinit var memDb: MemDao

    init {
        MyApp.appComponent.inject(viewModel = this)
    }

    fun getUserName():String {
        return storage.getField(APP_PREFERENCES_USERNAME)
    }

    fun getUserDescr():String {
        return storage.getField(APP_PREFERENCES_USERDESCR)
    }

    fun onInit () {
        val userId = storage.getUserId()
        showMyMemsFromDB(userId)
    }

    fun showMyMemsFromDB (userId: Int)  {
        memDb.getByCreatorId(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe{ Mem -> view.showDashboard(Mem)}
    }

}