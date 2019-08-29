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
import ru.chieffly.memoscope.view.activity.MemBrowserActivity


class FragmentProfile : Fragment(), OnMemItemClickListener {

    override fun onMemItemClick(pos: Int, mem: MemDto, shareImageView: ImageView) {
        println("НИФИГАСЕБЕ")

        val intent = Intent(requireContext(), MemBrowserActivity::class.java)
        intent.putExtra("MEM", mem)
//        intent.putExtra(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(shareImageView))

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
                //showAbout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun logoutDialog() {
        val dialog = LogoutDialogFragment()
        dialog.setTargetFragment(this, 333)
        dialog.show(fragmentManager!!.beginTransaction(), "dialog")
    }

    private fun configRecycler() : RecyclerView {
        val recyclerView = view?.findViewById(R.id.profileRecyclerView) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val stagGridLayoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.layoutManager = stagGridLayoutManager
        return recyclerView
    }

    fun showDashboard(mems: List<MemDto>) {
        val rcAdapter = MemRecyclerAdapter(mems, this)
        configRecycler().adapter = rcAdapter
    }


}