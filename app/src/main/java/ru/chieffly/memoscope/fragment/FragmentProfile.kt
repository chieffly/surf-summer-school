package ru.chieffly.memoscope.fragment

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.adapters.MemListAdapter
import ru.chieffly.memoscope.db.AppDatabase
import ru.chieffly.memoscope.model.MemDto
import ru.chieffly.memoscope.ui.LogoutDialogFragment
import ru.chieffly.memoscope.user.APP_PREFERENCES_USERDESCR
import ru.chieffly.memoscope.user.APP_PREFERENCES_USERNAME
import ru.chieffly.memoscope.user.UserStorage


class FragmentProfile : Fragment() {

    lateinit var txtUserName : TextView
    lateinit var txtUserDescription : TextView
    lateinit var DashProgressBar : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_person, null)
        setHasOptionsMenu(true)
        initViews(view)
        initListeners(view)

        return view
    }
    override fun onStart() {
        super.onStart()
        extractData()
    }
    private fun initListeners(view: View) {

    }

    private fun initViews(view: View) {
        val storage = UserStorage(requireActivity().application)

        txtUserName = view.findViewById(R.id.txtUserName)
        txtUserDescription = view.findViewById(R.id.txtUserDescription)
        DashProgressBar = view.findViewById(R.id.DashProgressBar)
        DashProgressBar.isVisible = false
        txtUserName.text = storage.getField(APP_PREFERENCES_USERNAME)
        txtUserDescription.text = storage.getField(APP_PREFERENCES_USERDESCR)

        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)


    }


    fun extractData() {
        val db = AppDatabase.getDatabase(requireContext())
        val memDB = db.memDao()
        val listFromDB = memDB.getByCreatorId(3232112)
        listFromDB.forEach{
            println("ID DB  "+it.id)
        }
        updateRecycleView(listFromDB)
        DashProgressBar.isVisible = false
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
            inflater.inflate(R.menu.app_menu, menu)

}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.main_menu_logout -> {
                logoutDialog()
                return true
            }
            R.id.main_menu_about -> {
                //showHelp()
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

    fun updateRecycleView(mems: List<MemDto>) {
        val recyclerView = view?.findViewById(R.id.profileRecyclerView) as RecyclerView
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