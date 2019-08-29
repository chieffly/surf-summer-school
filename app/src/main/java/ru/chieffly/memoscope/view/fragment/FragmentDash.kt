package ru.chieffly.memoscope.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.presenters.DashPresenter
import ru.chieffly.memoscope.view.activity.MemBrowserActivity
import ru.chieffly.memoscope.view.adapters.MemRecyclerAdapter
import ru.chieffly.memoscope.view.adapters.OnMemItemClickListener

const val RECYCLER_SPAN_COUNT = 2

class FragmentDash : Fragment(), OnMemItemClickListener {

    private lateinit var rcAdapter : MemRecyclerAdapter
    private val presenter = DashPresenter(this)
    private lateinit var progressBar: ProgressBar
    private lateinit var textFragment: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dash, container, false)
        initViews(view)
        presenter.initRecycler ()
        presenter.makeMemRequest()

        return view
    }

    private fun initViews(view: View) {
        textFragment = view.findViewById(R.id.textViewR) as TextView
        progressBar = view.findViewById(R.id.DashProgressBar) as ProgressBar
        showProgress()

        val mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout) as SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener {
            presenter.makeMemRequest()
            mSwipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onMemItemClick(pos: Int, mem: MemDto, shareImageView: ImageView) {
        val intent = Intent(requireContext(), MemBrowserActivity::class.java)
        intent.putExtra("MEM", mem)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            shareImageView,
            ViewCompat.getTransitionName(shareImageView)!!
        )
        startActivity(intent, options.toBundle())
    }


    fun showProgress() {
        textFragment.isVisible = false
        progressBar.isVisible = true
    }

    fun hideProgress() {
        textFragment.isVisible = false
        progressBar.isVisible = false
    }

    fun showRecyclerError() {
        textFragment.isVisible = true
        progressBar.isVisible = false
        textFragment.text = getString(R.string.err_dash)
    }

    fun configRecycler(mems: List<MemDto>) {
        //TODO view????
        val recyclerView = view?.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val stagGridLayoutManager = StaggeredGridLayoutManager(
            RECYCLER_SPAN_COUNT,
            StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.layoutManager = stagGridLayoutManager
        rcAdapter = MemRecyclerAdapter(mems, this)
        recyclerView.adapter = rcAdapter

    }


    fun getListAdapter() : MemRecyclerAdapter {
        return rcAdapter
    }
}