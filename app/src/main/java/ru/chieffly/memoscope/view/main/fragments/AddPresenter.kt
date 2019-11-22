package ru.chieffly.memoscope.view.main.fragments

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.db.MemDao
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.utils.UserStorage
import ru.chieffly.memoscope.view.main.fragments.FragmentAdd
import java.util.*
import javax.inject.Inject


class AddPresenter (val view: FragmentAdd) {
    @Inject
    lateinit var storage: UserStorage
    @Inject
    lateinit var memDb: MemDao
    init {
        MyApp.appComponent.inject(viewModel = this)
    }
    fun onSaveMem() {
        val mem = MemDto(
            0, // 0 - means id auto generates in bd
            view.getMemTitle(),
            view.getMemDescription(),
            false,
            Date().time / 1000,
            view.getImageLink(),
            storage.getUserId()
        )

        Single.fromCallable {
            memDb.insert(mem)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe()
    }



}