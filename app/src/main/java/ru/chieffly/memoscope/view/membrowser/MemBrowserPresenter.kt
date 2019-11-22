package ru.chieffly.memoscope.view.membrowser

import android.content.Intent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.db.MemDao
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.utils.APP_PREFERENCES_USERNAME
import ru.chieffly.memoscope.utils.UserStorage
import java.util.*
import javax.inject.Inject

class MemBrowserPresenter(val view: MemBrowserActivity) {

    @Inject
    lateinit var app: MyApp
    @Inject
    lateinit var storage: UserStorage
    @Inject
    lateinit var memDb: MemDao

    init {
        MyApp.appComponent.inject(viewModel = this)
    }

    fun onUpdateMem (mem : MemDto) {

        Single.fromCallable {
            memDb.update(mem)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    fun getUserName():String {
        return storage.getField(APP_PREFERENCES_USERNAME)
    }

    fun getCurentData (): Long {
        return (Date().time / 1000)
    }

    fun shareMem (mem: MemDto) {
        val shareIntent = Intent(Intent.ACTION_SEND_MULTIPLE)
        shareIntent.putExtra(Intent.EXTRA_TEXT, mem.title + " \n" + mem.description + "\n"+mem.photoUtl)
        shareIntent.type = "text/plain"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val chooser = Intent.createChooser(shareIntent,app.getString(R.string.share_menu))
        view.startActivity(chooser)
    }
}