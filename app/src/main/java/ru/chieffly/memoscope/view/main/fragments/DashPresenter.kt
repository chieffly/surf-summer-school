package ru.chieffly.memoscope.view.main.fragments

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.db.MemDao
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.net.MemApi
import ru.chieffly.memoscope.utils.UserStorage
import javax.inject.Inject

class DashPresenter(val view: FragmentDash) {

    @Inject
    lateinit var storage: UserStorage
    @Inject
    lateinit var memDb: MemDao
    @Inject
    lateinit var memApi: MemApi
    init {
        MyApp.appComponent.inject(viewModel = this)
    }
    fun makeMemRequest() {
        view.showProgress ()

        val token = storage.getToken()
        memApi.getMemes(token).enqueue(object : Callback<List<MemDto>> {
            override fun onFailure(call: Call<List<MemDto>>?, t: Throwable?) {
                view.showRecyclerError()
            }

            override fun onResponse(call: Call<List<MemDto>>?, response: Response<List<MemDto>>?) {
                if (response?.isSuccessful()!!) {
                    val listOfMems = response.body() as List<MemDto>//список мемов
                    addMemsInDB (listOfMems)
                    view.hideProgress()
                    showMemsFromDB()
                } else {
                    view.showRecyclerError()
                }
            }
        })
    }

    fun addMemsInDB (listOfMems: List<MemDto>) {
        listOfMems.forEach {
            Single.fromCallable {
                memDb.insert(it)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe()
        }
    }

    fun showMemsFromDB ()  {
        memDb.getall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe{listMem -> view.getListAdapter().updateList(listMem)}
    }
    fun initRecycler ()  {
        memDb.getall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe{listMem -> view.showDashboard(listMem)}
    }

}