package ru.chieffly.memoscope.presenters

import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.view.activity.MainActivity

class MainPresenter (val view: MainActivity) {


    fun onNavigationItemSelected(itemId : Int) : Boolean {
        when (itemId) {

            R.id.navigation_dashboard -> view.showFragmentDash()

            R.id.navigation_person -> view.showFragmentProfile()

            R.id.navigation_add -> view.showFragmentAdd()

            else -> return false
        }
        return true
    }
}