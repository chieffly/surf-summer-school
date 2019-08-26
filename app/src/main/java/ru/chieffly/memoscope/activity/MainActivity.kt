package ru.chieffly.memoscope.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.chieffly.memoscope.fragment.FragmentAdd
import ru.chieffly.memoscope.fragment.FragmentDash
import ru.chieffly.memoscope.fragment.FragmentPerson
import ru.chieffly.memoscope.R


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        loadFragment(FragmentDash())
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (item.getItemId()) {

            R.id.navigation_dashboard -> fragment = FragmentDash()

            R.id.navigation_person -> fragment = FragmentPerson()

            R.id.navigation_add -> fragment = FragmentAdd()
        }

        return loadFragment(fragment)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {

        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment!!)
                .commit()
            return true
        }
        return false
    }
}
