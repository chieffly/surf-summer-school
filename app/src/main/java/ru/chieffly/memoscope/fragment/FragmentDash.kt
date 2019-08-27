package ru.chieffly.memoscope.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.net.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chieffly.memoscope.user.UserStorage
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.adapters.MemListAdapter
import ru.chieffly.memoscope.db.AppDatabase


class FragmentDash : Fragment() {

    lateinit var progressBar : ProgressBar
    lateinit var textFragment : TextView
    private val ns = NetworkService.instance


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dash, container, false)
        textFragment = view.findViewById(R.id.textViewR) as TextView
        progressBar = view.findViewById(R.id.DashProgressBar) as ProgressBar
        textFragment.visibility= TextView.INVISIBLE

        makeMemRequest()


        val mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout) as SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener {
            makeMemRequest()
            mSwipeRefreshLayout.isRefreshing = false
        }

        return view;

    }

    fun makeMemRequest() {
        val storage = UserStorage(requireContext())
        val token = storage.getToken()
        println (token)
        ns.getMemApi().getMemes().enqueue(object : Callback<List<MemDto>> {
            override fun onFailure(call: Call<List<MemDto>>?, t: Throwable?) {
                progressBar.isVisible = false
                textFragment.isVisible = true
                textFragment.text = getString(R.string.err_dash)
            }
            override fun onResponse(call: Call<List<MemDto>>?, response: Response<List<MemDto>>?) {
                if (response?.isSuccessful()!!) {
                    val list = response.body() as List<MemDto>//список мемов

                    // ТЕСТ загрузка базы данных и отображение информации из нее
                    val db = AppDatabase.getDatabase(requireContext())
                    val memDB = db.memDao()
                    list.forEach{
                        println("ID "+it.id)
                        memDB.insert(it)
                    }

                    val listFromDB = memDB.all
                    updateRecycleView(listFromDB)
                    progressBar.isVisible = false

                } else {
                    progressBar.isVisible = false
                    textFragment.isVisible = true
                    textFragment.text = getString(R.string.err_dash)
                }
            }
        })
    }

    fun updateRecycleView(mems: List<MemDto>) {
        val recyclerView = view?.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.setHasFixedSize(true)

        val sGridLayoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.layoutManager = sGridLayoutManager


        val rcAdapter = MemListAdapter( mems)
        recyclerView.adapter = rcAdapter
    }

}