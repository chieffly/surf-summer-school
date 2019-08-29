package ru.chieffly.memoscope.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.chieffly.memoscope.view.fragment.FragmentAdd
import ru.chieffly.memoscope.view.fragment.FragmentDash
import ru.chieffly.memoscope.view.fragment.FragmentProfile
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.presenters.MainPresenter


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val presenter = MainPresenter(this)

    private val fragmentDash = FragmentDash()
    private val fragmentProfile = FragmentProfile()
    private val fragmentAdd = FragmentAdd()
    private var active = fragmentDash as Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragments()
        initViews()
    }

    fun initViews() {
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)
    }

    fun initFragments() {
        fragmentAdd.setParent(this)
        showFragmentDash()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return presenter.onNavigationItemSelected(item.getItemId())
    }


    fun showFragmentDash() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentDash)
            .commit()
        active = fragmentDash
        println("showFragmentDash")
    }

    fun showFragmentAdd() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentAdd)
            .commit()
        active = fragmentAdd

    }

    fun showFragmentProfile() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentProfile)
            .commit()
        active = fragmentProfile
    }

}
