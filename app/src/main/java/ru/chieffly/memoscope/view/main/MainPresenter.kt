package ru.chieffly.memoscope.view.main

import ru.chieffly.memoscope.R

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