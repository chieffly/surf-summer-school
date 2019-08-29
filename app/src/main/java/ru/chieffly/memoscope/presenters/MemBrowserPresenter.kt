package ru.chieffly.memoscope.presenters

import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.net.NetworkService
import ru.chieffly.memoscope.utils.APP_PREFERENCES_USERNAME
import ru.chieffly.memoscope.view.activity.MemBrowserActivity
import java.util.*

class MemBrowserPresenter(val view: MemBrowserActivity) {
    private val ns = NetworkService.instance
    private val app: MyApp = MyApp.applicationContext() as MyApp




    fun onUpdateMem (mem : MemDto) {

        Single.fromCallable {
            app.getDB().update(mem)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    fun getUserName():String {
        return app.getStorage().getField(APP_PREFERENCES_USERNAME)
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