package ru.chieffly.memoscope.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.presenters.ProfilePresenter
import ru.chieffly.memoscope.view.dialogs.LogoutDialogFragment
import ru.chieffly.memoscope.view.adapters.MemRecyclerAdapter
import ru.chieffly.memoscope.view.adapters.OnMemItemClickListener
import androidx.core.view.ViewCompat
import androidx.core.app.ActivityOptionsCompat
import android.content.Intent
import ru.chieffly.memoscope.view.activity.MEM_SERIALIZE_ID
import ru.chieffly.memoscope.view.activity.MemBrowserActivity
import java.util.ArrayList

const val LOGOUT_REQUEST_CODE = 333

class FragmentProfile : Fragment(), OnMemItemClickListener {
    lateinit var rcAdapter : MemRecyclerAdapter

    override fun onMemItemClick(pos: Int, mem: MemDto, shareImageView: ImageView) {
        val intent = Intent(requireContext(), MemBrowserActivity::class.java)
        intent.putExtra(MEM_SERIALIZE_ID, mem)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            shareImageView,
            ViewCompat.getTransitionName(shareImageView)!!
        )

        startActivity(intent, options.toBundle())    }

    private val presenter = ProfilePresenter(this)

    lateinit var txtUserName: TextView
    lateinit var txtUserDescription: TextView
    lateinit var DashProgressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_person, null)
        setHasOptionsMenu(true)
        initViews(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.onInit ()

    }

    private fun initViews(view: View) {
        txtUserName = view.findViewById(R.id.txtUserName)
        txtUserDescription = view.findViewById(R.id.txtUserDescription)
        DashProgressBar = view.findViewById(R.id.DashProgressBar)
        DashProgressBar.isVisible = false
        txtUserName.text = presenter.getUserName()
        txtUserDescription.text = presenter.getUserDescr()
        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        initRecyclerView(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.app_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_menu_logout -> {
                logoutDialog()
                return true
            }
            R.id.main_menu_about -> {
                //TODO showAbout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun logoutDialog() {
        val dialog = LogoutDialogFragment()
        dialog.setTargetFragment(this, LOGOUT_REQUEST_CODE)
        dialog.show(fragmentManager!!.beginTransaction(), "dialog")
    }

    private fun initRecyclerView(view : View) {
        val recyclerView = view.findViewById(R.id.profileRecyclerView) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val stagGridLayoutManager = StaggeredGridLayoutManager(
            RECYCLER_SPAN_COUNT,
            StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.layoutManager = stagGridLayoutManager
        val mems = ArrayList<MemDto>()
        rcAdapter = MemRecyclerAdapter(mems, this)
        recyclerView.adapter = rcAdapter
    }


    fun showDashboard(mems: List<MemDto>) {
        rcAdapter.mems = mems
        rcAdapter.notifyDataSetChanged()

    }


}