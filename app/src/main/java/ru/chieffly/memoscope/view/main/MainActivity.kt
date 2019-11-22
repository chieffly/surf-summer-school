package ru.chieffly.memoscope.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.chieffly.memoscope.R
import ru.chieffly.memoscope.view.main.fragments.FragmentAdd
import ru.chieffly.memoscope.view.main.fragments.FragmentDash
import ru.chieffly.memoscope.view.main.fragments.FragmentProfile




private const val CAMERA_REQUEST_CODE = 101
private const val WRITE_REQUEST_CODE = 102
private const val READ_REQUEST_CODE = 103

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
        setupPermissions()
    }
    private fun setupPermissions() {
        val camerapermission = Manifest.permission.CAMERA
        val writepermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val readpermission = Manifest.permission.READ_EXTERNAL_STORAGE

        if (ContextCompat.checkSelfPermission(this,camerapermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(camerapermission),
                CAMERA_REQUEST_CODE
            )
        }
        if (ContextCompat.checkSelfPermission(this,writepermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(writepermission),
                WRITE_REQUEST_CODE
            )
        }
        if (ContextCompat.checkSelfPermission(this,readpermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(readpermission),
                READ_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    println("Permission has been denied by user ")
                } else {
                    println("Permission has been granted by user")
                }
            }
            WRITE_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    println("WRITE Permission has been denied by user ")
                } else {
                    println("WRITE Permission has been granted by user")
                }
            }
            READ_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    println("WRITE Permission has been denied by user ")
                } else {
                    println("WRITE Permission has been granted by user")
                }
            }
        }
    }

    private fun initViews() {
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun initFragments() {
        fragmentAdd.setParent(this)
        showFragmentDash()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return presenter.onNavigationItemSelected(item.getItemId())
    }


    fun showFragment(fragme: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragme)
            .commit()
        active = fragme
    }

    fun showFragmentDash() {
        showFragment(fragmentDash)
    }

    fun showFragmentAdd() {
        showFragment(fragmentAdd)
    }

    fun showFragmentProfile() {
        showFragment(fragmentProfile)

    }

}
