package ru.chieffly.memoscope.presenters

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.view.fragment.FragmentAdd
import java.util.*


class AddPresenter (val view: FragmentAdd) {
    private val app: MyApp = MyApp.applicationContext() as MyApp



    fun onSaveMem() {
        val mem = MemDto(
            0, // 0 - means id auto generates in bd
            view.getMemTitle(),
            view.getMemDescription(),
            false,
            Date().time / 1000,
            view.getImageLink(),
            app.getStorage().getUserId()
        )

        Single.fromCallable {
            app.getDB().insert(mem)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe()
    }



}