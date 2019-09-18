package ru.chieffly.memoscope.presenters

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chieffly.memoscope.MyApp
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.net.NetworkService
import ru.chieffly.memoscope.view.fragment.FragmentDash

class DashPresenter(val view: FragmentDash) {
    private val ns = NetworkService.instance
    private val app: MyApp = MyApp.applicationContext() as MyApp


    fun makeMemRequest() {
        view.showProgress ()

        val token = app.getStorage().getToken()
        ns.getMemApi().getMemes(token).enqueue(object : Callback<List<MemDto>> {
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
                app.getDB().insert(it)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe()
        }
    }

    fun showMemsFromDB ()  {
        app.getDB().getall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe{listMem -> view.getListAdapter().updateList(listMem)}
    }
    fun initRecycler ()  {
        app.getDB().getall()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe{listMem -> view.showDashboard(listMem)}
    }

}